package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.common.results.ResultBuild;
import com.tony.demo.microservice.mud.common.results.Results;
import com.tony.demo.microservice.mud.trade.content.AppCode;
import com.tony.demo.microservice.mud.trade.content.AppTypeCode;
import com.tony.demo.microservice.mud.trade.content.SerialNumberUtil;
import com.tony.demo.microservice.mud.trade.exception.BusinessException;
import com.tony.demo.microservice.mud.trade.exception.InvalidParameterException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordErrorException;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * 基金交易申购服务
 * <p>
 * Created by Tony on 27/05/2017.
 */
@Service("fundTradePurchaseService")
public class FundTradePurchaseService extends FundTradeBasic {

    private Logger logger = LoggerFactory.getLogger(FundTradePurchaseService.class);

    private static final String UN_USED = "";
    private final String APP_TYPE = String.valueOf(AppTypeCode.WE_CHAT_XLICAI.getCode().intValue());
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
    private SimpleTradeHandler simpleTradeHandler;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 组合预申购 异步购买
     *
     * @return
     */
    public Results prePurchase(FundTradePurchaseRequest trade) throws Exception {
        try {
            logger.debug("开始处理用户{}购买组合{}的申请.", trade.getUserId(), trade.getPortfolioId());
            userIsLocked(trade.getUserId());
            tryLogin(trade.getIdNumber(), trade.getPassword());
            TradeHandleRequest tradeCommand = new TradeHandleRequest(1);
            BeanUtils.copyProperties(trade, tradeCommand);
            for (FundTradePurchaseProperty fund : trade.getFundProperties()) {
                fund.setStatus(UNPAID);
                UserFundOrdersPortfolioDetailRequest orderReq = saveOrder(trade, fund);
                savePortfolioUserDetail(trade, fund);
                tradeCommand.addOrder(orderReq.getOrderId());
                logger.debug("成功记录用户{}基金{}的申购数据, 等待发起异步处理申请.", trade.getUserId(), fund.getFundCode());
            }
            saveOrderMaster(trade);
            storePortfolioTrade(trade);
            simpleTradeHandler.publish(tradeCommand);
            logger.info("发起用户{}异步购买组合{}请求成功.", trade.getUserId(), trade.getPortfolioId());
            return ResultBuild.success("申购成功");
        } catch (Exception e) {
            logger.error("用户基金组合预申购失败", e);
            if (e instanceof TradePasswordErrorException)
                lockUser(trade.getUserId());
            return ResultBuild.failed(e);
        }
    }

    /**
     * 基金申购
     *
     * @param tradeHandle
     * @throws Exception
     */
    public void purchase(TradeHandleRequest tradeHandle) throws Exception {
        logger.debug("开始处理用户[{}]申购请求.", tradeHandle.getIdNumber());
        for (String orderId : tradeHandle.getOrders()) {
            UserFundOrdersPortfolioDetailResponse order = fundOrdersPortfolioDetailService.findByOrderId(orderId);
            UserFundOrdersPortfolioDetailRequest request = new UserFundOrdersPortfolioDetailRequest();
            request.setOrderId(order.getOrderId());
            request.setFundCode(order.getFundCode());
            try {
                PurchaseResponseBean purchaseRes = hbFundTradeIntegration.purchase(order.getFundCode(), "",
                        tradeHandle.getTradeAcco(), order.getAmount().toString(), tradeHandle.getCapitalMode(),
                        tradeHandle.getPassword(), APP_ID, tradeHandle.getIdNumber(), UN_USED, "", APP_TYPE);
                if ("true".equals(purchaseRes.getSuccessful())) {
                    request.setTtOrderNo(purchaseRes.getResults().get("applySerialStr"));
                    request.setStatus(ACCEPT);
                    request.setPayStatus(1);
                    logger.info("用户[{}]购买基金{}成功.", tradeHandle.getIdNumber(), order.getFundCode());
                } else {
                    request.setStatus(FAILED);
                    request.setPayStatus(0);
                    logger.info("用户[{}]购买基金{}失败. XX响应信息为: {}", tradeHandle.getIdNumber(), order.getFundCode(), purchaseRes.toString());
                }
                setRemoteTradeInfo(tradeHandle, request, order, purchaseRes.getResults().get("applySerialStr"));
            } catch (Exception e) {
                logger.error("用户[{}]基金申购处理异常.", tradeHandle.getIdNumber(), e);
                request.setStatus(MANUAL);
                request.setPayStatus(0);
            } finally {
                logger.debug("用户[{}]申购处理完成，修改订单状态.", tradeHandle.getIdNumber());
                fundOrdersPortfolioDetailService.modifyByPrimaryKey(request);
            }
        }
    }


    /**
     * 落地用户组合基金持仓明细数据表
     * <p>
     * 在申购场景下，因为XX的基金都是非T+0.
     * 所以，在中间状态的情况下，份额不同步到持仓数据表.
     *
     * @param trade 申购交易申请明细
     * @param fund  基金明细
     * @throws Exception
     */
    @Transactional
    private void savePortfolioUserDetail(FundTradePurchaseRequest trade, FundTradePurchaseProperty fund) throws Exception {
        FundPortfolioUserDetailResponse userHistoryDetail = fundPortfolioUserDetailService.findUserHistoryDetail(trade.getUserId(), trade.getPortfolioId(), fund.getFundCode());
        if (userHistoryDetail == null || userHistoryDetail.getId() == null) {
            FundPortfolioUserDetailRequest request = new FundPortfolioUserDetailRequest();
            request.setPortfolioId(trade.getPortfolioId());
            request.setFundCode(fund.getFundCode());
            request.setUserId(trade.getUserId());
            request.setSn(fund.getSn());
            request.setPercentBegin(fund.getProportion());
            // 当前占比 当前基金市值/组合总市值
            request.setPercent(fund.getAmount().divide(new BigDecimal(trade.getAmount()), 2, ROUND_HALF_UP));
            // 当前市值 因为交易处于中间状态 默认0
            request.setMarketVal(BigDecimal.ZERO);
            // 当前份额 因为交易处于中间状态 默认0
            request.setShare(BigDecimal.ZERO);
            request.setShareId("");
            request.setCost(fund.getAmount());
            request.setBankCode(trade.getBankSerial());
            request.setBankCardNo(trade.getBankAcco());
            request.setBankName(trade.getBankName());
            fundPortfolioUserDetailService.save(request);
        } else {
            // 更新投入成本
            FundPortfolioUserDetailRequest request = new FundPortfolioUserDetailRequest();
            request.setId(userHistoryDetail.getId());
            request.setCost(fund.getAmount().add(userHistoryDetail.getCost()));
            fundPortfolioUserDetailService.modifyShareByPK(request);
        }
    }

    /**
     * 落地用户组合持仓数据
     * 交易中间状态份额、金额数据不落地
     *
     * @param trade 交易申请
     */
    private void storePortfolioTrade(FundTradePurchaseRequest trade) throws Exception {
        FundPortfolioUserMasterResponse userHistory = fundPortfolioUserMasterService.findUserPosition(trade.getUserId(), trade.getPortfolioId());
        if (userHistory.getId() == null) {
            FundPortfolioMasterResponse portfolioMaster = fundPortfolioService.findByPortfolioId(trade.getPortfolioId());
            savePortfolioUserMaster(trade, portfolioMaster);
        } else {
            // 更新投入成本
            FundPortfolioUserMasterRequest request = new FundPortfolioUserMasterRequest();
            request.setId(userHistory.getId());
            request.setPortfolioInvestedCost(userHistory.getPortfolioInvestedCost().add(new BigDecimal(trade.getAmount())));
            fundPortfolioUserMasterService.modify(request);
        }
    }

    /**
     * 落地用户组合数据
     *
     * @param trade           用户交易申请
     * @param portfolioMaster 组合产品明细
     */
    private void savePortfolioUserMaster(FundTradePurchaseRequest trade, FundPortfolioMasterResponse portfolioMaster) throws Exception {
        FundPortfolioUserMasterRequest request = new FundPortfolioUserMasterRequest();
        request.setPortfolioId(trade.getPortfolioId());
        request.setPortfolioName(portfolioMaster.getPortfolioName());
        request.setPortfolioRiskType(portfolioMaster.getRiskPrefer());
        request.setUserId(trade.getUserId());
        request.setAipCycle((short) trade.getAipCycle());
        // todo 组合净值
        request.setPortfolioNet(BigDecimal.ZERO);
        // 组合当前市值 投入的总金额 交易中状态，为0
        request.setPortfolioMarketVal(BigDecimal.ZERO);
        // 组合的份额 查询接口查询 交易中状态，为0
        request.setPortfolioShare(BigDecimal.ZERO);
        // 组合总成本 投入的总金额
        request.setPortfolioInvestedCost(new BigDecimal(trade.getAmount()));
        // 累计收益 定时任务更新
        request.setPortfolioAccumulatedProfit(BigDecimal.ZERO);
        request.setPortfolioTplId(0);
        request.setAipType((byte) trade.getAipCycle());
        request.setRebalanceType((byte) 0);
        request.setYtdReturn(portfolioMaster.getYtdReturn());
        request.setYear1Return(portfolioMaster.getYear1Return());
        request.setTotalReturn(portfolioMaster.getTotalReturn());
        request.setAddTime((int) (System.currentTimeMillis() / 1000));
        request.setLastUpdateTime(new Date());
        request.setStockFundMarketVal(BigDecimal.ZERO);
        request.setBondFundMarketVal(BigDecimal.ZERO);
        request.setCurrencyFundMarketVal(BigDecimal.ZERO);
        fundPortfolioUserMasterService.save(request);
    }

    /**
     * 落地订单主表
     *
     * @param trade
     * @throws Exception
     */
    private void saveOrderMaster(FundTradePurchaseRequest trade) throws Exception {
        logger.debug("记录用户{}订单主表信息", trade.getUserId());
        OrderMasterRequest orderMasterRequest = new OrderMasterRequest();
        orderMasterRequest.setPortfolioId(trade.getPortfolioId());
        FundPortfolioMasterResponse portfolio = fundPortfolioService.findByPortfolioId(trade.getPortfolioId());
        orderMasterRequest.setPortfolioName(portfolio.getPortfolioName());
        orderMasterRequest.setType(1);
        orderMasterRequest.setAmount(new BigDecimal(trade.getAmount()));
        orderMasterRequest.setShare(BigDecimal.ZERO);
        orderMasterRequest.setRatio(BigDecimal.ZERO);
        orderMasterRequest.setCreateTime(new Date());
        orderMasterService.save(orderMasterRequest);
    }

    /**
     * 落地订单交易数据
     *
     * @param trade 用户交易申请
     * @param fund  基金产品明细
     */
    private UserFundOrdersPortfolioDetailRequest saveOrder(FundTradePurchaseRequest trade, FundTradePurchaseProperty fund) throws Exception {
        UserFundOrdersPortfolioDetailRequest request = new UserFundOrdersPortfolioDetailRequest();
        // 本地系统订单号
        request.setOrderId(SerialNumberUtil.build());
        request.setTtOrderNo("");
        request.setFundCode(fund.getFundCode());
        request.setUserId(trade.getUserId());
        request.setTtAccountNo("");
        request.setPortfolioId(trade.getPortfolioId());
        request.setAmount(fund.getAmount());
        request.setShare(BigDecimal.ZERO);
        // 基金份额 申购为空，赎回固定份额
        request.setShareId("");
        request.setOrderTime((int) (System.currentTimeMillis() / 1000));
        // 基金订单返回的申请时间
        request.setOrderType(1); // 申购
        request.setWorkday(0); // 所属工作日 工作日暂时不考虑
        request.setBankCode(trade.getBankSerial());
        request.setBankCardNo(trade.getBankAcco());
        request.setFee(trade.getFundFees().get(fund.getFundCode()));
        request.setApplyTime((int) System.currentTimeMillis() / 1000);
        request.setConfirmDate(0);
        request.setComfirmAmount(BigDecimal.ZERO);
        request.setComfirmShare(BigDecimal.ZERO);
        request.setLastUpdateTime(new Date());
        request.setStatus(fund.getStatus());
        request.setPayStatus(-1);
        request.setFailReason("");
        fundOrdersPortfolioDetailService.save(request);
        return request;
    }

    /**
     * 计算组合申购基金明细信息
     * 1. 基金账号
     * 2. 基金名称
     * 3. 基金占比
     * 4. 基金申购金额
     * 5. 组合手续费
     *
     * @param trade 组合申购交易申请
     */
    public void buildPurchaseFundDetails(FundTradePurchaseRequest trade) throws Exception {
        BigDecimal sumAmount = new BigDecimal(trade.getAmount());
        List<RaFundPortfolioDetailResponse> fundDetails = fundPortfolioDetailService.findByPortfolioId(trade.getPortfolioId());
        for (RaFundPortfolioDetailResponse fundDetail : fundDetails) {
            FundTradePurchaseProperty fundProperty = new FundTradePurchaseProperty();
            fundProperty.setFundCode(fundDetail.getFundCode());
            fundProperty.setFundName(fundDetail.getFundName());
            fundProperty.setProportion(fundDetail.getPercentBegin());
            fundProperty.setAmount(sumAmount.multiply(fundDetail.getPercentBegin()).setScale(2, BigDecimal.ROUND_HALF_UP));
            fundProperty.setSn(fundDetail.getSn());
            trade.getFundProperties().add(fundProperty);
            Product product = productService.findByFundCode(fundDetail.getFundCode());
            BigDecimal fundFee = fundProperty.getAmount().multiply(new BigDecimal(product.getFeeRate())).setScale(2, BigDecimal.ROUND_HALF_UP);
            trade.getFundFees().put(fundDetail.getFundCode(), fundFee);
            trade.setFee(trade.getFee().add(fundFee));
        }
    }

    /**
     * 校验申购交易参数
     *
     * @param trade 交易申请
     */
    public void verify(FundTradePurchaseRequest trade) throws Exception {
        parameterVerify(trade);
        paymentChannelVerify(trade);
    }

    /**
     * 校验申购交易参数
     *
     * @param trade 交易申请
     */
    private void parameterVerify(FundTradePurchaseRequest trade) throws Exception {
        if (StringUtils.isBlank(trade.getBankSerial()))
            throw new InvalidParameterException("银行代码不能为空");
        if (StringUtils.isBlank(trade.getBankAcco()))
            throw new InvalidParameterException("银行卡号不能为空");
        if (StringUtils.isBlank(trade.getIdNumber()))
            throw new InvalidParameterException("用户身份证号不能为空");
        if (trade.getPortfolioId() == null || trade.getPortfolioId() <= 0)
            throw new InvalidParameterException("申购组合不能为空");
        if (StringUtils.isBlank(trade.getAmount()))
            throw new InvalidParameterException("申购金额不能为空");
        List<RaFundPortfolioDetailResponse> fundDetails = fundPortfolioDetailService.findByPortfolioId(trade.getPortfolioId());
        BigDecimal miniAmount = fundPortfolioDetailService.getMiniAmount(fundDetails);
        if (miniAmount.compareTo(new BigDecimal(trade.getAmount())) > 0)
            throw new InvalidParameterException("申购金额不得低于" + miniAmount + "元");
    }

    /**
     * 支付渠道验证
     * 校验是否和历史已存在交易的支付渠道相驳
     */
    private void paymentChannelVerify(FundTradePurchaseRequest trade) throws Exception {
        List<FundPortfolioUserDetailResponse> userDetails = fundPortfolioUserDetailService.findByUserIdAndPortfolioId(trade.getUserId(), trade.getPortfolioId());
        if (userDetails != null && userDetails.size() > 0) {
            FundPortfolioUserDetailResponse userDetail = userDetails.get(0);
            if (!trade.getBankAcco().equals(userDetail.getBankCardNo()))
                throw new BusinessException("您只能使用银行卡[" + userDetail.getBankCardNo() + "]完成申购.");
        }
    }

}
