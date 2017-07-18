package com.tony.demo.microservice.mud.trade.controller;

import com.tony.demo.microservice.mud.common.restful.RestResponse;
import com.tony.demo.microservice.mud.common.results.Results;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordErrorException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordLockException;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradePurchaseApplyRequest;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradePurchaseRequest;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundTradeRedemptionRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundRedemptionWarnResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RiskTradeResponse;
import com.tony.demo.microservice.mud.trade.service.risk.RiskTestService;
import com.tony.demo.microservice.mud.trade.service.trade.FundPortfolioDetailService;
import com.tony.demo.microservice.mud.trade.service.trade.FundPortfolioUserMasterService;
import com.tony.demo.microservice.mud.trade.service.trade.FundTradePurchaseService;
import com.tony.demo.microservice.mud.trade.service.trade.FundTradeRedemptionService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static com.tony.demo.microservice.mud.common.restful.ApiResponseBuild.*;

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
    private final FundPortfolioDetailService fundPortfolioDetailService;

    @Autowired
    public FundComposeTradeController(FundTradePurchaseService fundTradePurchaseService, FundTradeRedemptionService fundTradeRedemptionService, RiskTestService riskTestService, FundPortfolioUserMasterService portfolioUserMasterService, FundPortfolioDetailService fundPortfolioDetailService) {
        this.fundTradePurchaseService = fundTradePurchaseService;
        this.fundTradeRedemptionService = fundTradeRedemptionService;
        this.riskTestService = riskTestService;
        this.portfolioUserMasterService = portfolioUserMasterService;
        this.fundPortfolioDetailService = fundPortfolioDetailService;
    }

    /**
     * 用户申购交易判断是否属于风险交易
     *
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "用户申购风险评定", notes = "根据组合ID，判定用申购的基金是否属于风险(高于系统给定的评测结果)交易")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "portfolioId", value = "组合ID", required = true, dataType = "Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取数据成功(response.data)", response = RiskTradeResponse.class),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "purchases/risk/{portfolioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> hasRisk(HttpServletRequest httpReq, @PathVariable("portfolioId") int portfolioId) {
        try {
            // TODO 分布式session中获取用户ID
            RiskTradeResponse risk = riskTestService.isRiskTrade(1, portfolioId);
            logger.info("评测用户[{}]购买的基金[{}]是否存在风险结果:{}", 1, portfolioId, risk.isHasRisk());
            return ok(risk);
        } catch (Exception e) {
            logger.error("Failed to check risk.", e);
            return serverError();
        }
    }

    /**
     * 根据组合id 获取组合最低起购金额
     *
     * @param portfolioId
     * @return
     */
    @ApiOperation(value = "获取组合最低起购金额", notes = "根据组合id 计算组合最低起够金额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "portfolioId", value = "组合ID", required = true, dataType = "Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取数据成功(response.data)", response = RestResponse.class),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "portfolios/lowest/{portfolioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> getLowestMoney(@PathVariable("portfolioId") Integer portfolioId) {
        try {
            BigDecimal miniAmount = fundPortfolioDetailService.getMiniAmount(portfolioId);
            return ok(miniAmount);
        } catch (Exception e) {
            logger.error("Failed to get lowest money for purchase.", e);
            return serverError();
        }
    }

    /**
     * 组合申购申请
     *
     * @param applyReq 基金申购交易请求
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "基金交易申购申请", notes = "基金申购金额、资金来源、申购组合等基础信息")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "申请成功"),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "purchases/apply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> purchaseApply(HttpServletRequest httpReq, @RequestBody FundTradePurchaseApplyRequest applyReq) {
        try {
            // TODO get user info from session.
            FundTradePurchaseRequest req = new FundTradePurchaseRequest();
            BeanUtils.copyProperties(applyReq, req);
            logger.info("用户[{}]发起申购申请", 1);
            req.setIdNumber("");
            req.setUserId(1);
            fundTradePurchaseService.verify(req);
            httpReq.getSession().setAttribute(PURCHASE_TRADE, req);
            return created();
        } catch (Exception e) {
            logger.error("Failed to apply purchase.", e);
            return serverError(e.getMessage());
        }
    }

    /**
     * 获取基金申购确认信息
     *
     * @param fundTrade 基金申购交易请求
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "获取基金交易申购确认信息", notes = "获取确认基金申购金额、资金来源、申购组合等基础信息")
    @RequestMapping(value = "purchases/confirm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取确认信息成功", response = FundTradePurchaseRequest.class),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 404, message = "未找到申购信息"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    public @ResponseBody
    ResponseEntity<RestResponse> purchaseConfirmInfo(HttpServletRequest httpReq,
                                                     @ApiIgnore @ModelAttribute(PURCHASE_TRADE) FundTradePurchaseRequest fundTrade) {
        try {
            if (fundTrade == null)
                return notFound("未找到用户申购信息.");
            logger.info("构建用户{}申购组合的确认信息", fundTrade.getUserId());
            fundTradePurchaseService.buildPurchaseFundDetails(fundTrade);
            return ok(fundTrade);
        } catch (Exception e) {
            logger.error("Failed to confirm purchase info.", e);
            return serverError();
        }
    }

    /**
     * 申购操作
     *
     * @param fundTrade 基金申购交易请求
     * @param password  交易密码
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "基金交易申购提交购买", notes = "提交申购组合的申请, 确认购买")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "交易密码", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "交易提交成功", response = Results.class),
            @ApiResponse(code = 400, message = "交易密码错误"),
            @ApiResponse(code = 423, message = "交易密码次数达到上限，锁定"),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 404, message = "未找到申购信息"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "purchases", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> doPurchase(HttpServletRequest httpReq,
                                            @ApiIgnore @ModelAttribute(PURCHASE_TRADE) FundTradePurchaseRequest fundTrade,
                                            @RequestParam String password) {
        try {
            if (fundTrade == null)
                return notFound("未找到用户申购信息.");
            if (StringUtils.isBlank(password))
                return badRequest("交易密码不能为空");
            fundTrade.setPassword(password);
            logger.debug("提交用户{}申购申请", fundTrade.getUserId());
            return created(fundTradePurchaseService.prePurchase(fundTrade));
        } catch (Exception e) {
            logger.error("Failed to submit purchase.", e);
            if (e instanceof TradePasswordErrorException)
                return badRequest("请输入正确的交易密码");
            if (e instanceof TradePasswordLockException)
                return locked("交易密码已经输错3次锁定");
            return serverError();
        } finally {
            // clear password
            if (fundTrade != null)
                fundTrade.setPassword("******");
        }
    }

    /**
     * 赎回组合，年限检查是否到期
     *
     * @param httpReq     HttpServletRequest
     * @param portfolioId 组合id
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "组合基金赎回年限检查", notes = "检查赎回的基金是否在申购年限范围内，如果申购年限未到，提示用户是否继续操作.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "portfolioId", value = "组合ID", required = true, dataType = "Integer")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "年限检查成功", response = FundRedemptionWarnResponse.class),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "redemption/age-limit/{portfolioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> ageLimitCheck(HttpServletRequest httpReq,
                                               @PathVariable("portfolioId") int portfolioId) {
        try {
            // TODO get user info from session
            return ok(portfolioUserMasterService.ageLimitCheck(1, portfolioId));
        } catch (Exception e) {
            logger.error("Failed to process purchase.", e);
            return serverError(e.getMessage());
        }
    }

    /**
     * 组合赎回申请
     *
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "组合基金赎回申请", notes = "按比例赎回指定的基金.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "portfolioId", value = "组合ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "proportion", value = "赎回比例(0-1小数，例如：75%应该是0.75)", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "申请成功", response = FundTradeRedemptionRequest.class),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "redemption/apply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> redemption(HttpServletRequest httpReq,
                                            @RequestParam("portfolioId") Integer portfolioId,
                                            @RequestParam("proportion") String proportion) {
        try {
            // TODO get user info from session
            FundTradeRedemptionRequest redemptionReq = new FundTradeRedemptionRequest(portfolioId, 1, proportion);
            redemptionReq.setIdNumber("");
            fundTradeRedemptionService.buildRedemptionRequest(redemptionReq);
            httpReq.getSession().setAttribute(REDEMPTION_TRADE, redemptionReq);
            return created(redemptionReq);
        } catch (Exception e) {
            logger.error("组合赎回申请异常.", e);
            return serverError(e.getMessage());
        }
    }

    /**
     * 获取组合赎回确认信息
     *
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "获取组合基金赎回确认信息", notes = "获取按比例赎回指定的基金的确认信息.")
    @RequestMapping(value = "redemption/confirm", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "确认成功", response = FundTradeRedemptionRequest.class),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    public @ResponseBody
    ResponseEntity<RestResponse> redemptionConfirm(HttpServletRequest httpReq,
                                                   @ApiIgnore @ModelAttribute FundTradeRedemptionRequest redemptionReq) {
        try {
            fundTradeRedemptionService.buildRedemptionAck(redemptionReq);
            httpReq.getSession().setAttribute(REDEMPTION_TRADE, redemptionReq);
            return ok(redemptionReq);
        } catch (Exception e) {
            logger.error("组合赎回申请异常.", e);
            return serverError(e.getMessage());
        }
    }

    /**
     * 组合赎回
     *
     * @return Json Object, see {@link }
     */
    @ApiOperation(value = "组合基金赎回提交", notes = "提交按比例赎回指定的基金申请操作.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "交易密码", required = true, dataType = "String"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "交易提交成功", response = Results.class),
            @ApiResponse(code = 400, message = "交易密码错误"),
            @ApiResponse(code = 423, message = "交易密码次数达到上限，锁定"),
            @ApiResponse(code = 401, message = "登录超时"),
            @ApiResponse(code = 404, message = "未找到赎回信息"),
            @ApiResponse(code = 500, message = "内部服务错误，请和管理员联系.", response = RestResponse.class)
    })
    @RequestMapping(value = "redemption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<RestResponse> doRedemption(HttpServletRequest httpReq,
                                              @ApiIgnore @ModelAttribute FundTradeRedemptionRequest redemptionReq,
                                              @RequestParam String password) {
        try {
            if (redemptionReq == null)
                return notFound("未找到用户赎回信息.");
            if (StringUtils.isBlank(password))
                return badRequest("交易密码不能为空");
            redemptionReq.setPassword(password);
            return created(fundTradeRedemptionService.preRedemption(redemptionReq));
        } catch (Exception e) {
            logger.error("组合赎回申请异常.", e);
            if (e instanceof TradePasswordErrorException)
                return badRequest("请输入正确的交易密码");
            if (e instanceof TradePasswordLockException)
                return locked("交易密码已经输错3次锁定");
            return serverError(e.getMessage());
        } finally {
            if (redemptionReq != null)
                redemptionReq.setPassword("******");
        }
    }


}
