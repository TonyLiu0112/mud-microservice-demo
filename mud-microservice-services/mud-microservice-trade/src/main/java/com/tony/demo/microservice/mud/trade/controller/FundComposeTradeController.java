package com.tony.demo.microservice.mud.trade.controller;

import com.tony.demo.microservice.mud.common.results.ResultBuild;
import com.tony.demo.microservice.mud.trade.service.FundPortfolioUserMasterService;
import com.tony.demo.microservice.mud.trade.service.FundTradePurchaseService;
import com.tony.demo.microservice.mud.trade.service.FundTradeRedemptionService;
import com.tony.demo.microservice.mud.trade.service.RiskTestService;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradePurchaseRequest;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradeRedemptionRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserMasterResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 智能投顾
 * <p>
 * 组合基金交易
 * <p>
 * Created by Tony on 27/05/2017.
 */
@Controller
@RequestMapping("ra/fund/compose")
@SessionAttributes(value = {"purchaseTrade", "redemptionTrade"},
        types = {FundTradePurchaseRequest.class, FundTradeRedemptionRequest.class})
public class FundComposeTradeController {

    private Logger logger = LoggerFactory.getLogger(FundComposeTradeController.class);

    private final String PURCHASE_TRADE = "purchaseTrade";
    private final String REDEMPTION_TRADE = "redemptionTrade";
    private final FundTradePurchaseService fundTradePurchaseService;
    private final FundTradeRedemptionService fundTradeRedemptionService;
    private final RiskTestService riskTestService;
    private final FundPortfolioUserMasterService portfolioUserMasterService;

    @Autowired
    public FundComposeTradeController(FundTradePurchaseService fundTradePurchaseService, FundTradeRedemptionService fundTradeRedemptionService, RiskTestService riskTestService, FundPortfolioUserMasterService portfolioUserMasterService) {
        this.fundTradePurchaseService = fundTradePurchaseService;
        this.fundTradeRedemptionService = fundTradeRedemptionService;
        this.riskTestService = riskTestService;
        this.portfolioUserMasterService = portfolioUserMasterService;
    }

    /**
     * 用户申购交易判断是否属于风险交易
     *
     * @return
     */
    @RequestMapping("purchase/hasRisk")
    public @ResponseBody
    Object hasRisk(HttpServletRequest httpReq, @RequestParam("portfolioId") int portfolioId) {
        try {
            // TODO: 25/06/2017 Get user info from session.
            int userId = 0;
            boolean risk = riskTestService.isRiskTrade(userId, portfolioId);
            Map<String, Object> riskMap = new HashMap<>();
            riskMap.put("hasRisk", risk);
            return ResultBuild.success(riskMap);
        } catch (Exception e) {
            logger.error("Failed to process purchase.", e);
            return ResultBuild.failed(e);
        }
    }

    /**
     * 组合申购申请
     *
     * @param fundTrade 基金申购交易请求
     * @return Json Object, see {@link com.tony.demo.microservice.mud.common.results.Results}
     */
    @RequestMapping(value = "purchase/apply", method = RequestMethod.POST)
    public @ResponseBody
    Object purchase(HttpServletRequest httpReq, FundTradePurchaseRequest fundTrade) {
        try {
            // TODO: 25/06/2017 Get user info from session.
            int userId = 0;
            fundTrade.setIdNumber("");
            fundTrade.setUserId(userId);
            fundTradePurchaseService.verify(fundTrade);
            httpReq.getSession().setAttribute(PURCHASE_TRADE, fundTrade);
            return ResultBuild.success();
        } catch (Exception e) {
            logger.error("Failed to process purchase.", e);
            return ResultBuild.failed(e);
        }
    }

    /**
     * 基金申购确认
     *
     * @param fundTrade 基金申购交易请求
     * @return Json Object, see {@link com.tony.demo.microservice.mud.common.results.Results}
     */
    @RequestMapping(value = "purchase/confirm", method = RequestMethod.POST)
    public @ResponseBody
    Object purchaseConfirmInfo(HttpServletRequest httpReq, @ModelAttribute(PURCHASE_TRADE) FundTradePurchaseRequest fundTrade) {
        try {
            if (fundTrade == null)
                return ResultBuild.failed("未找到用户申购信息.");
            fundTradePurchaseService.buildPurchaseFundDetails(fundTrade);
            return ResultBuild.success(fundTrade);
        } catch (Exception e) {
            logger.error("Failed to process purchase.", e);
            return ResultBuild.failed(e);
        }
    }

    /**
     * 申购操作
     *
     * @param fundTrade 基金申购交易请求
     * @param password  交易密码
     * @return Json Object, see {@link com.tony.demo.microservice.mud.common.results.Results}
     */
    @RequestMapping(value = "purchase/submit", method = RequestMethod.POST)
    public @ResponseBody
    Object doPurchase(HttpServletRequest httpReq, @ModelAttribute(PURCHASE_TRADE) FundTradePurchaseRequest fundTrade,
                      @RequestParam String password) {
        try {
            if (fundTrade == null)
                return ResultBuild.failed("未找到用户申购信息.");
            if (StringUtils.isBlank(password))
                return ResultBuild.failed("交易密码不能为空.");
            fundTrade.setPassword(password);
            return fundTradePurchaseService.purchase(fundTrade);
        } catch (Exception e) {
            logger.error("Failed to process purchase.", e);
            return ResultBuild.failed(e);
        } finally {
            // clear password
            if (fundTrade != null)
                fundTrade.setPassword("******");
        }
    }

    /**
     * 获取用户组合持仓数据
     *
     * @param httpReq
     * @param portfolioId
     * @return
     */
    @RequestMapping("redemption/getUserPosition")
    public @ResponseBody
    Object getPosition(HttpServletRequest httpReq, @RequestParam("portfolioId") int portfolioId) {
        try {
            // TODO: 25/06/2017 Get user info from session.
            int userId = 0;
            FundPortfolioUserMasterResponse userMaster = portfolioUserMasterService.findUserPosition(userId, portfolioId);
            return ResultBuild.success(userMaster);
        } catch (Exception e) {
            logger.error("Failed to process purchase.", e);
            return ResultBuild.failed(e);
        }

    }

    /**
     * 组合赎回申请
     *
     * @return Json Object, see {@link com.tony.demo.microservice.mud.common.results.Results}
     */
    @RequestMapping(value = "redemption/apply", method = RequestMethod.GET)
    public @ResponseBody
    Object redemption(HttpServletRequest httpReq,
                      @RequestParam("portfolioId") Integer portfolioId,
                      @RequestParam("proportion") String proportion) {
        try {
            // TODO: 25/06/2017 Get user info from session.
            int userId = 0;
            FundTradeRedemptionRequest redemptionReq = new FundTradeRedemptionRequest(portfolioId, userId, proportion);
            redemptionReq.setIdNumber("");
            fundTradeRedemptionService.buildRedemptionRequest(redemptionReq);
            httpReq.getSession().setAttribute(REDEMPTION_TRADE, redemptionReq);
            return ResultBuild.success(redemptionReq);
        } catch (Exception e) {
            logger.error("组合赎回申请异常.", e);
            return ResultBuild.failed(e);
        }
    }

    /**
     * 组合赎回确认
     *
     * @return Json Object, see {@link com.tony.demo.microservice.mud.common.results.Results}
     */
    @RequestMapping(value = "redemption/confirm", method = RequestMethod.GET)
    public @ResponseBody
    Object redemptionConfirm(HttpServletRequest httpReq, @ModelAttribute FundTradeRedemptionRequest redemptionReq) {
        try {
            fundTradeRedemptionService.buildRedemptionAck(redemptionReq);
            httpReq.getSession().setAttribute(REDEMPTION_TRADE, redemptionReq);
            return ResultBuild.success(redemptionReq);
        } catch (Exception e) {
            logger.error("组合赎回申请异常.", e);
            return ResultBuild.failed(e);
        }
    }

    /**
     * 组合赎回
     *
     * @return Json Object, see {@link com.tony.demo.microservice.mud.common.results.Results}
     */
    @RequestMapping(value = "redemption/submit", method = RequestMethod.GET)
    public @ResponseBody
    Object doRedemption(HttpServletRequest httpReq,
                        @ModelAttribute FundTradeRedemptionRequest redemptionReq,
                        @RequestParam String password) {
        try {
            if (redemptionReq == null)
                return ResultBuild.failed("未找到用户申购信息.");
            if (StringUtils.isBlank(password))
                return ResultBuild.failed("交易密码不能为空.");
            redemptionReq.setPassword(password);
            return fundTradeRedemptionService.redemption(redemptionReq);
        } catch (Exception e) {
            logger.error("组合赎回申请异常.", e);
            return ResultBuild.failed(e);
        } finally {
            if (redemptionReq != null)
                redemptionReq.setPassword("******");
        }
    }


}
