package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.common.results.ResultBuild;
import com.tony.demo.microservice.mud.common.results.Results;
import com.tony.demo.microservice.mud.trade.content.SerialNumberUtil;
import com.tony.demo.microservice.mud.trade.exception.BusinessException;
import com.tony.demo.microservice.mud.trade.exception.InvalidParameterException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordErrorException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordLockException;
import com.tony.demo.microservice.mud.trade.integration.TradeClient;
import com.tony.demo.microservice.mud.trade.integration.dto.PurchaseResultDTO;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeHistoryDTO;
import com.tony.demo.microservice.mud.trade.service.bean.req.*;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioMasterResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserDetailResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserMasterResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundPortfolioDetailResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
public class FundTradePurchaseService extends FundTradeBasic {

    private Logger logger = LoggerFactory.getLogger(FundTradePurchaseService.class);

    private static final String UN_USED = "";
    private final String APP_TYPE = "1";
    private final String APP_ID = "2";

    @Autowired
    private FundPortfolioService fundPortfolioService;

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

    /**
     * 组合申购
     * <p>
     * 申购约定
     * </br>
     * <li>不支持不同支付渠道的方式</li>
     *
     * @param trade 交易申请明细
     */
    public Results purchase(FundTradePurchaseRequest trade) throws Exception {
        try {
            userIsLocked(trade.getUserId());
            for (FundTradePurchaseProperty fundInfo : trade.getFundProperties()) {
                singlePurchase(trade, fundInfo);
            }
            storePortfolioTrade(trade);
        } catch (Exception e) {
            logger.error("基金申购失败", e);
            return ResultBuild.failed(e);
        }
        return ResultBuild.success("申购成功");
    }

    /**
     * 单个基金申购
     * 申购失败后，尝试重新申购，最大重试5次
     * 最大次数失败后，落地交易数据，交易状态标记为失败
     *
     * @param trade 交易申请
     * @param fund  基金产品明细
     */
    private void singlePurchase(FundTradePurchaseRequest trade, FundTradePurchaseProperty fund) throws Exception {
        PurchaseResultDTO purchaseRes = null;
        try {
            purchaseRes = tradeClient.purchase(fund.getFundCode(), "",
                    trade.getTradeAcco(), fund.getAmount().toString(), trade.getCapitalMode(), trade.getPassword(), APP_ID, trade.getIdNumber(), UN_USED, "", APP_TYPE);
            if ("0000".equals(purchaseRes.getCode())) {
                storePurchaseSuccessData(trade, fund, purchaseRes);
            } else {
                if (PASSWORD_ERROR.equals(purchaseRes.getCode()))
                    throw new TradePasswordErrorException();
            }
        } catch (TradePasswordErrorException e) {
            lockUser(trade.getUserId());
            throw e;
        } catch (Exception e) {
            logger.error("单只基金[" + fund.getFundCode() + "]申购失败.", e);
            try {
                String errorMsg = e.getMessage();
                if (purchaseRes != null)
                    errorMsg = purchaseRes.getMessage();
                storePurchaseFailedData(trade, fund, errorMsg);
            } catch (Exception se) {
                logger.error("存储基金交易失败数据异常.", se);
            }
        }
    }

    /**
     * 落地基金申购失败交易数据
     *
     * @param trade    申购交易申请明细
     * @param fund     基金明细
     * @param errorMsg 错误消息
     * @throws Exception
     */
    private void storePurchaseFailedData(FundTradePurchaseRequest trade, FundTradePurchaseProperty fund, String errorMsg) throws Exception {
        fund.setStatus(APPLYING);
        savePortfolioUserDetail(trade, fund);
        saveOrder(trade, fund, null, errorMsg);
    }

    /**
     * 落地基金申购成功交易数据
     * 份额数据等待对账程序批处理完成
     *
     * @param trade       申购交易申请明细
     * @param fund        基金明细
     * @param purchaseRes 基金公司申购返回结果
     * @throws Exception
     */
    private void storePurchaseSuccessData(FundTradePurchaseRequest trade, FundTradePurchaseProperty fund, PurchaseResultDTO purchaseRes) throws Exception {
        fund.setStatus(ACCEPT);
        TradeHistoryDTO remoteTrade = new TradeHistoryDTO();
        String errorMsg = "";
        try {
            remoteTrade = findRemoteTradeRecord(trade.getIdNumber(), fund.getFundCode(), purchaseRes.getApplySerialStr());
        } catch (Exception e) {
            logger.error("查询基金申购交易数据异常.", e);
            remoteTrade.setApplyShare("0");
            errorMsg = "基金申购成功，但获取申购交易数据失败.";
            fund.setStatus(APPLYING);
        }
        savePortfolioUserDetail(trade, fund);
        saveOrder(trade, fund, remoteTrade, errorMsg);
    }

    /**
     * 落地用户组合基金持仓明细数据表
     * <p>
     * 在申购场景下，因为华宝的基金都是非T+0.
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
            fundPortfolioUserDetailService.save(request);
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
        if (userHistory == null) {
            FundPortfolioMasterResponse portfolioMaster = fundPortfolioService.findByPortfolioId(trade.getPortfolioId());
            savePortfolioUserMaster(trade, portfolioMaster);
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
        // todo 已投期数
        request.setAipCycle((short) 0);
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
        fundPortfolioUserMasterService.save(request);
    }

    /**
     * 落地订单交易数据
     *
     * @param trade       用户交易申请
     * @param fund        基金产品明细
     * @param remoteTrade 基金公司交易明细
     * @param errorMsg    交易失败信息
     */
    private void saveOrder(FundTradePurchaseRequest trade, FundTradePurchaseProperty fund, TradeHistoryDTO remoteTrade, String errorMsg) throws Exception {
        UserFundOrdersPortfolioDetailRequest request = new UserFundOrdersPortfolioDetailRequest();
        // 本地系统订单号
        request.setOrderId(SerialNumberUtil.build());
        request.setFundCode(fund.getFundCode());
        request.setUserId(trade.getUserId());
        request.setTtAccountNo("");
        request.setPortfolioId(trade.getPortfolioId());
        request.setAmount(fund.getAmount());
        // 基金份额 申购为空，赎回固定份额
        request.setShareId("");
        request.setOrderTime((int) (System.currentTimeMillis() / 1000));
        // 基金订单返回的申请时间
        request.setOrderType(1); // 申购
        request.setWorkday(0);   // 所属工作日
        request.setBankCode(trade.getBankSerial());
        request.setBankCardNo(trade.getBankAcco());
        // todo 手续费
        request.setFee(BigDecimal.ZERO);
        request.setFailReason(errorMsg);
        request.setLastUpdateTime(new Date());
        request.setStatus(fund.getStatus());
        // 基金公司交易数据处理
        if (fund.getStatus() == ACCEPT) { // 申购成功
            if (remoteTrade != null) {
                request.setTtOrderNo(remoteTrade.getApplySerial());
                request.setShare(new BigDecimal(remoteTrade.getApplyShare()));
                Date date = new Date();// DateUtils.toDate(String.format("%s %s", remoteTrade.getApplyDate(), remoteTrade.getApplyTime()), "yyyy-MM-dd HH:mi:ss");
                if (date != null) {
                    Long applyTime = date.getTime();
                    request.setApplyTime(applyTime.intValue() / 1000);
                }
            }
            request.setPayStatus(2); // 处理中
        }
        if (fund.getStatus() == APPLYING) { // 申购失败
            request.setPayStatus(0); // 失败
        }
        fundOrdersPortfolioDetailService.save(request);
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
            fundProperty.setAmount(sumAmount.multiply(fundDetail.getPercentBegin()));
            fundProperty.setSn(fundDetail.getSn());
            trade.getFundProperties().add(fundProperty);
        }
        // TODO: 14/06/2017 手续费如何计算
        trade.setFee(BigDecimal.ZERO);
    }

    /**
     * 校验申购交易参数
     *
     * @param trade 交易申请
     */
    public void verify(FundTradePurchaseRequest trade) throws Exception {
        List<RaFundPortfolioDetailResponse> fundDetails = fundPortfolioDetailService.findByPortfolioId(trade.getPortfolioId());
        parameterVerify(trade, fundDetails);
        paymentChannelVerify(trade);
    }

    /**
     * 校验申购交易参数
     *
     * @param trade       交易申请
     * @param fundDetails 基金产品明细
     */
    private void parameterVerify(FundTradePurchaseRequest trade, List<RaFundPortfolioDetailResponse> fundDetails) throws Exception {
        if (userIsLocked(trade.getUserId()))
            throw new TradePasswordLockException();
        if (StringUtils.isBlank(trade.getBankSerial()))
            throw new InvalidParameterException("银行代码不能为空");
        if (StringUtils.isBlank(trade.getBankAcco()))
            throw new InvalidParameterException("银行卡号不能为空");
        if (StringUtils.isBlank(trade.getIdNumber()))
            throw new InvalidParameterException("用户身份证号不能为空");
        if (trade.getPortfolioId() <= 0)
            throw new InvalidParameterException("申购组合不能为空");
        if (StringUtils.isBlank(trade.getAmount()))
            throw new InvalidParameterException("申购金额不能为空");
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
