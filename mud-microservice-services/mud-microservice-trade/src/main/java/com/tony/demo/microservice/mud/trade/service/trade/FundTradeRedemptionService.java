package com.tony.demo.microservice.mud.trade.service.trade;

import com.alibaba.fastjson.JSONObject;
import com.tony.demo.microservice.mud.common.results.ResultBuild;
import com.tony.demo.microservice.mud.common.results.Results;
import com.tony.demo.microservice.mud.trade.content.AppCode;
import com.tony.demo.microservice.mud.trade.content.SerialNumberUtil;
import com.tony.demo.microservice.mud.trade.exception.BusinessException;
import com.tony.demo.microservice.mud.trade.exception.InvalidParameterException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordErrorException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordLockException;
import com.tony.demo.microservice.mud.trade.integration.HBFundTradeIntegration;
import com.tony.demo.microservice.mud.trade.service.bean.req.*;
import com.tony.demo.microservice.mud.trade.service.bean.res.*;
import com.tony.demo.microservice.mud.trade.service.product.ProductService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组合基金交易赎回服务
 * <p>
 * Created by Tony on 12/06/2017.
 */
@Service("fundTradeRedemptionService")
public class FundTradeRedemptionService extends FundTradeBasic {

    private Logger logger = LoggerFactory.getLogger(FundTradeRedemptionService.class);

    private static final String DEFAULT_SALE_WAY = "0";
    private final String APP_TYPE = "0";
    private final String APP_ID = AppCode.APP_ID_XLICAI.getCode();

    @Autowired
    private FundPortfolioService fundPortfolioService;

    @Autowired
    private FundPortfolioDetailService fundPortfolioDetailService;

    @Autowired
    private FundPortfolioUserMasterService fundPortfolioUserMasterService;

    @Autowired
    private FundPortfolioUserDetailService fundPortfolioUserDetailService;

    @Autowired
    private FundOrdersPortfolioDetailService fundOrdersPortfolioDetailService;

    @Autowired
    private HBFundTradeIntegration hbFundTradeIntegration;

    @Autowired
    private FundNetService fundNetService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SimpleTradeHandler simpleTradeHandler;

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 基金预赎回
     *
     * @param trade 基金赎回请求信息
     * @return
     */
    @Transactional
    public Results preRedemption(FundTradeRedemptionRequest trade) throws Exception {
        try {
            verify(trade);
            tryLogin(trade.getIdNumber(), trade.getPassword());
            TradeHandleRequest tradeCommand = new TradeHandleRequest(2);
            BeanUtils.copyProperties(trade, tradeCommand);
            for (FundTradeRedemptionProperty fund : trade.getFunds()) {
                // 更新用户组合基金持仓数据表份额、市值
                fundPortfolioUserDetailService.modifyShare4Redemption(fund.getShare().toString(), trade.getPortfolioId(), fund.getFundCode(), trade.getUserId());
                // 更新用户组合持仓份额、总投入成本和组合总市值
                fundPortfolioUserMasterService.modifyShare4Redemption(fund.getShare().toString(), trade.getPortfolioId(), fund.getFundCode(), trade.getUserId());
                UserFundOrdersPortfolioDetailRequest orderReq = saveOrder(trade, fund);
                tradeCommand.addOrder(orderReq.getOrderId());
            }
            saveOrderMaster(trade);
            simpleTradeHandler.publish(tradeCommand);
            return ResultBuild.success("组合赎回成功.");
        } catch (Exception e) {
            logger.error("组合赎回失败.", e);
            if (e instanceof TradePasswordErrorException)
                lockUser(trade.getUserId());
            return ResultBuild.failed(e);
        }
    }

    private void saveOrderMaster(FundTradeRedemptionRequest trade) throws Exception {
        OrderMasterRequest orderMasterRequest = new OrderMasterRequest();
        orderMasterRequest.setPortfolioId(trade.getPortfolioId());
        FundPortfolioMasterResponse portfolio = fundPortfolioService.findByPortfolioId(trade.getPortfolioId());
        orderMasterRequest.setPortfolioName(portfolio.getPortfolioName());
        orderMasterRequest.setType(2);
        orderMasterRequest.setAmount(BigDecimal.ZERO);
        orderMasterRequest.setShare(BigDecimal.ZERO);
        for (FundTradeRedemptionProperty property : trade.getFunds())
            orderMasterRequest.setShare(orderMasterRequest.getShare().add(property.getShare()));
        orderMasterRequest.setRatio(new BigDecimal(trade.getProportion()));
        orderMasterRequest.setCreateTime(new Date());
        orderMasterService.save(orderMasterRequest);
    }

    /**
     * 赎回操作
     *
     * @param handleRequest
     * @throws Exception
     */
    public void redemption(TradeHandleRequest handleRequest) throws Exception {
        for (String orderId : handleRequest.getOrders()) {
            UserFundOrdersPortfolioDetailRequest request = new UserFundOrdersPortfolioDetailRequest();
            UserFundOrdersPortfolioDetailResponse order = fundOrdersPortfolioDetailService.findByOrderId(orderId);
            request.setOrderId(order.getOrderId());
            request.setFundCode(order.getFundCode());
            try {
                SaleResponseBean integrationResponse = hbFundTradeIntegration.sale(order.getFundCode(), "", handleRequest.getTradeAcco(),
                        order.getShare().toString(), handleRequest.getPassword(), APP_ID, handleRequest.getIdNumber(), "", "",
                        APP_TYPE, DEFAULT_SALE_WAY);
                if ("true".equals(integrationResponse.getSuccessful())) {
                    request.setTtOrderNo(integrationResponse.getResults().get("applySerialStr"));
                    request.setStatus(ACCEPT);
                    request.setPayStatus(1);
                } else {
                    request.setStatus(FAILED);
                    request.setPayStatus(0);
                    rollbackShare(order);
                }
                setRemoteTradeInfo(handleRequest, request, order, integrationResponse.getResults().get("applySerialStr"));
            } catch (Exception e) {
                logger.error("", e);
                request.setStatus(MANUAL);
                request.setPayStatus(0);
                rollbackShare(order);
            } finally {
                fundOrdersPortfolioDetailService.modifyByPrimaryKey(request);
            }

        }
    }

    /**
     * 回滚份额信息
     *
     * @param order
     * @throws Exception
     */
    @Transactional
    private void rollbackShare(UserFundOrdersPortfolioDetailResponse order) throws Exception {
        fundPortfolioUserDetailService.modifyShare4Redemption(order.getShare().toString(), order.getPortfolioId(), order.getFundCode(), order.getUserId());
        fundPortfolioUserMasterService.modifyShare4Redemption(order.getShare().toString(), order.getPortfolioId(), order.getFundCode(), order.getUserId());
    }

    /**
     * 落地订单交易数据
     *
     * @param trade 用户交易申请
     * @param fund  基金产品明细
     */
    @Transactional
    private UserFundOrdersPortfolioDetailRequest saveOrder(FundTradeRedemptionRequest trade, FundTradeRedemptionProperty fund) throws Exception {
        UserFundOrdersPortfolioDetailRequest request = new UserFundOrdersPortfolioDetailRequest();
        RaFundNetResponse netResponse = fundNetService.findLatestByFundCode(fund.getFundCode());
        // 本地系统订单号
        request.setOrderId(SerialNumberUtil.build());
        request.setFundCode(fund.getFundCode());
        request.setUserId(trade.getUserId());
        request.setTtAccountNo("");
        request.setShare(fund.getShare());
        request.setPortfolioId(trade.getPortfolioId());
        request.setAmount(fund.getShare().multiply(netResponse.getNet()));
        // 基金份额 赎回为空，赎回固定份额
        request.setShareId("");
        request.setOrderTime((int) (System.currentTimeMillis() / 1000));
        // 基金订单返回的申请时间
        request.setOrderType(2); // 赎回
        request.setWorkday(0);   // 所属工作日
        request.setBankCode(trade.getBankCode());
        request.setBankCardNo(trade.getBankCardNo());
        // 手续费
        request.setFee(trade.getFundFees().get(fund.getFundCode()));
        request.setFailReason("");
        request.setLastUpdateTime(new Date());
        request.setPayStatus(2); // 处理中
        request.setStatus(UNPAID);
        request.setTtOrderNo("");
        request.setApplyTime(0);
        request.setConfirmDate(0);
        request.setComfirmShare(BigDecimal.ZERO);
        request.setComfirmAmount(BigDecimal.ZERO);
        request.setComfirmNav(BigDecimal.ZERO);
        fundOrdersPortfolioDetailService.save(request);
        return request;
    }

    /**
     * 校验赎回交易参数
     *
     * @param trade 交易申请
     */
    private void verify(FundTradeRedemptionRequest trade) throws Exception {
        if (userIsLocked(trade.getUserId()))
            throw new TradePasswordLockException();
        if (StringUtils.isBlank(trade.getBankCode()))
            throw new InvalidParameterException("银行代码不能为空");
        if (StringUtils.isBlank(trade.getBankCardNo()))
            throw new InvalidParameterException("银行卡号不能为空");
        if (StringUtils.isBlank(trade.getIdNumber()))
            throw new InvalidParameterException("用户身份证号不能为空");
        if (StringUtils.isBlank(trade.getPassword()))
            throw new InvalidParameterException("交易密码不能为空");
        if (trade.getPortfolioId() <= 0)
            throw new InvalidParameterException("赎回组合不能为空");
        if (StringUtils.isBlank(trade.getProportion()))
            throw new InvalidParameterException("赎回比例不能为空");
    }

    /**
     * 构建赎回请求对象
     * 1. 根据赎回比例计算赎回份额
     * 2. 赎回的账户基本信息
     *
     * @param redemptionReq 用户赎回请求
     */
    public void buildRedemptionRequest(FundTradeRedemptionRequest redemptionReq) throws Exception {
        BigDecimal proportion = new BigDecimal(redemptionReq.getProportion());
        List<FundPortfolioUserDetailResponse> records = fundPortfolioUserDetailService.findByUserIdAndPortfolioId(redemptionReq.getUserId(), redemptionReq.getPortfolioId());
        if (records == null || records.size() == 0)
            throw new BusinessException("未获取到用户可赎回的组合数据");
        List<FundTradeRedemptionProperty> funds = new ArrayList<>();
        for (FundPortfolioUserDetailResponse record : records) {
            RaFundPortfolioDetailResponse fundDetail = fundPortfolioDetailService.findFundDetail(record.getPortfolioId(), record.getFundCode());
            funds.add(new FundTradeRedemptionProperty(record.getFundCode(), fundDetail.getFundName(), record.getShare().multiply(proportion).setScale(2, BigDecimal.ROUND_DOWN)));
            redemptionReq.setBankCardNo(record.getBankCardNo());
            redemptionReq.setBankCode(record.getBankCode());
            redemptionReq.setBankName(record.getBankName());
        }
        redemptionReq.setFunds(funds);
    }

    /**
     * 计算组合基金赎回手续费和交易账号信息
     *
     * @param redemptionReq 赎回请求信息
     */
    public void buildRedemptionAck(FundTradeRedemptionRequest redemptionReq) throws Exception {
        // 手续费
        for (FundTradeRedemptionProperty property : redemptionReq.getFunds()) {
            Product product = productService.findByFundCode(property.getFundCode());
            RaFundNetResponse net = fundNetService.findLatestByFundCode(property.getFundCode());
            BigDecimal fee = property.getShare().multiply(net.getNet()).multiply(new BigDecimal(product.getFeeRate()));
            redemptionReq.getFundFees().put(property.getFundCode(), fee);
            redemptionReq.setFee(redemptionReq.getFee().add(fee).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        // 获取用户申购的交易账号信息 组合的各个基金赎回交易账号应一致
        if (StringUtils.isBlank(redemptionReq.getTradeAcco())) {
            FundTradeRedemptionProperty fundProperty = redemptionReq.getFunds().get(0);
            JSONObject tradeAccJson = getTradeAccountInfo(redemptionReq.getIdNumber(), fundProperty.getFundCode(), redemptionReq.getBankCardNo());
            Assert.notNull(tradeAccJson, "用户[" + redemptionReq.getIdNumber() + "] 赎回[" + fundProperty.getFundCode() + "]失败，未获取到银行代码[" + redemptionReq.getBankCode() + "]的交易账号信息.");
            redemptionReq.setTradeAcco(tradeAccJson.get("tradeAcco").toString());
        }
    }

}
