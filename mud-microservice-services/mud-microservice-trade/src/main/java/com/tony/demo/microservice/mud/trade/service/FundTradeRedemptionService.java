package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.common.results.ResultBuild;
import com.tony.demo.microservice.mud.common.results.Results;
import com.tony.demo.microservice.mud.trade.content.SerialNumberUtil;
import com.tony.demo.microservice.mud.trade.exception.*;
import com.tony.demo.microservice.mud.trade.integration.TradeClient;
import com.tony.demo.microservice.mud.trade.integration.dto.RedemptionResultDTO;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeAccountDTO;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeHistoryDTO;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradeRedemptionProperty;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradeRedemptionRequest;
import com.tony.demo.microservice.mud.trade.service.bean.req.UserFundOrdersPortfolioDetailRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserDetailResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundNetResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundPortfolioDetailResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
public class FundTradeRedemptionService extends FundTradeBasic {

    private Logger logger = LoggerFactory.getLogger(FundTradeRedemptionService.class);

    private static final String DEFAULT_SALE_WAY = "0";
    private final String APP_TYPE = "1";
    private final String APP_ID = "2";

    @Autowired
    private FundPortfolioDetailService fundPortfolioDetailService;

    @Autowired
    private FundPortfolioUserMasterService fundPortfolioUserMasterService;

    @Autowired
    private FundPortfolioUserDetailService fundPortfolioUserDetailService;

    @Autowired
    private UserFundOrdersPortfolioDetailService fundOrdersPortfolioDetailService;

    @Autowired
    private TradeClient tradeClient;

    @Autowired
    private FundNetService fundNetService;

    /**
     * 基金赎回
     *
     * @param trade 基金赎回请求信息
     * @return
     */
    public Results redemption(FundTradeRedemptionRequest trade) throws Exception {
        try {
            verify(trade);
            for (FundTradeRedemptionProperty fundTradeRedemptionProperty : trade.getFunds()) {
                singleRedemption(trade, fundTradeRedemptionProperty);
            }
        } catch (Exception e) {
            logger.error("组合赎回失败.", e);
            return ResultBuild.failed(e);
        }
        return ResultBuild.success("组合赎回成功.");
    }

    /**
     * 单个基金赎回
     *
     * @param trade 基金赎回请求信息
     * @param fund  当前赎回的基金
     * @throws Exception
     */
    private void singleRedemption(FundTradeRedemptionRequest trade, FundTradeRedemptionProperty fund) throws Exception {
        RedemptionResultDTO redemptionResultDTO = null;
        try {
            redemptionResultDTO = tradeClient.redemption(fund.getFundCode(), "", trade.getTradeAcc(),
                    fund.getShare().toString(), trade.getPassword(), APP_ID, trade.getIdNumber(), "HBXY", "",
                    APP_TYPE, DEFAULT_SALE_WAY);
            if ("0000".equals(redemptionResultDTO.getCode())) {
                storeRedemptionDataSuccess(trade, fund, redemptionResultDTO);
            } else {
                if (PASSWORD_ERROR.equals(redemptionResultDTO.getMessage()))
                    throw new TradePasswordErrorException();
            }
        } catch (TradePasswordErrorException e) {
            lockUser(trade.getUserId());
            throw e;
        } catch (Exception e) {
            logger.error("赎回基金失败.", e);
            String errorMsg = e.getMessage();
            if (redemptionResultDTO != null)
                errorMsg = redemptionResultDTO.getMessage();
            storeRedemptionDataFailed(trade, fund, errorMsg);
        }
    }

    private void storeRedemptionDataFailed(FundTradeRedemptionRequest trade, FundTradeRedemptionProperty fund, String errorMsg) throws Exception {
        fund.setStatus(APPLYING);
        saveOrder(trade, fund, null, errorMsg);
    }

    /**
     * 落地基金赎回交易成功数据
     *
     * @param trade 交易申请明细
     * @param fund  基金明细
     */
    @Transactional
    private void storeRedemptionDataSuccess(FundTradeRedemptionRequest trade,
                                            FundTradeRedemptionProperty fund,
                                            RedemptionResultDTO integrationResponse) throws Exception {
        fund.setStatus(ACCEPT);
        TradeHistoryDTO remoteTrade = null;
        String errorMsg = "";
        try {
            remoteTrade = findRemoteTradeRecord(trade.getIdNumber(), fund.getFundCode(), integrationResponse.getApplySerialStr());
        } catch (RemoteInvokeException e) {
            logger.error("查询基金赎回交易数据异常.", e);
            errorMsg = "基金赎回成功，但获取赎回交易数据失败.";
            fund.setStatus(APPLYING);
        }
        // 更新用户组合基金持仓数据表份额、市值
        fundPortfolioUserDetailService.modifyShare4Redemption(fund.getShare().toString(), trade.getPortfolioId(), fund.getFundCode(), trade.getUserId());
        // 更新用户组合持仓份额、总投入成本和组合总市值
        fundPortfolioUserMasterService.modifyShare4Redemption(fund.getShare().toString(), trade.getPortfolioId(), fund.getFundCode(), trade.getUserId());
        // 赎回申请成功 落地订单交易流水表
        saveOrder(trade, fund, remoteTrade, errorMsg);
    }

    /**
     * 落地订单交易数据
     *
     * @param trade       用户交易申请
     * @param fund        基金产品明细
     * @param remoteTrade 基金公司交易明细
     * @param errorMsg    交易失败信息
     */
    @Transactional
    private void saveOrder(FundTradeRedemptionRequest trade, FundTradeRedemptionProperty fund, TradeHistoryDTO remoteTrade, String errorMsg) throws Exception {
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
        // TODO 手续费
        request.setFee(trade.getFee());
        request.setFailReason(errorMsg);
        request.setLastUpdateTime(new Date());
        request.setPayStatus(2); // 处理中
        request.setStatus(fund.getStatus());
        if (fund.getStatus() == ACCEPT) {
            if (remoteTrade != null) {
                request.setTtOrderNo(remoteTrade.getApplySerial());
                Date date = new Date();// DateUtils.toDate(String.format("%s %s", remoteTrade.getApplyDate(), remoteTrade.getApplyTime()), "yyyy-MM-dd HH:mi:ss");
                if (date != null) {
                    Long applyTime = date.getTime();
                    request.setApplyTime(applyTime.intValue() / 1000);
                }
            }

        }
        fundOrdersPortfolioDetailService.save(request);
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
     * 1. 根据赎回比例计算赎回金额
     * 2. 赎回的账户基本信息
     *
     * @param redemptionRequest 用户赎回请求
     */
    public void buildRedemptionRequest(FundTradeRedemptionRequest redemptionRequest) throws Exception {
        BigDecimal proportion = new BigDecimal(redemptionRequest.getProportion());
        List<FundPortfolioUserDetailResponse> records = fundPortfolioUserDetailService.findByUserIdAndPortfolioId(redemptionRequest.getUserId(), redemptionRequest.getPortfolioId());
        if (records == null || records.size() == 0)
            throw new BusinessException("未获取到用户可赎回的组合数据");
        List<FundTradeRedemptionProperty> funds = new ArrayList<>();
        for (FundPortfolioUserDetailResponse record : records) {
            record.getShare().multiply(proportion);
            RaFundPortfolioDetailResponse fundDetail = fundPortfolioDetailService.findFundDetail(record.getPortfolioId(), record.getFundCode());
            funds.add(new FundTradeRedemptionProperty(record.getFundCode(), fundDetail.getFundName(), record.getShare().multiply(proportion)));
            redemptionRequest.setBankCardNo(record.getBankCardNo());
            redemptionRequest.setBankCode(record.getBankCode());
            redemptionRequest.setBankName(record.getBankName());
        }
        redemptionRequest.setFunds(funds);
    }

    /**
     * 计算组合基金赎回手续费和交易账号信息
     *
     * @param redemptionReq 赎回请求信息
     */
    public void buildRedemptionAck(FundTradeRedemptionRequest redemptionReq) throws Exception {
        // TODO: 15/06/2017 手续费
        // 获取用户申购的交易账号信息 组合的各个基金赎回交易账号应一致
        if (StringUtils.isBlank(redemptionReq.getTradeAcc())) {
            FundTradeRedemptionProperty fundProperty = redemptionReq.getFunds().get(0);
            TradeAccountDTO tradeAccountInfo = getTradeAccountInfo(redemptionReq.getIdNumber(), fundProperty.getFundCode(), redemptionReq.getBankCode());
            Assert.notNull(tradeAccountInfo, "用户[" + redemptionReq.getIdNumber() + "] 赎回[" + fundProperty.getFundCode() + "]失败，未获取到银行代码[" + redemptionReq.getBankCode() + "]的交易账号信息.");
            redemptionReq.setTradeAcc(tradeAccountInfo.getTradeAcco());
        }
    }
}
