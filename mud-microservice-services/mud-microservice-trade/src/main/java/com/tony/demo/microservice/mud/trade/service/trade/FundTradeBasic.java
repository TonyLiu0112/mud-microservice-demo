package com.tony.demo.microservice.mud.trade.service.trade;

import com.alibaba.fastjson.JSONObject;
import com.tony.demo.microservice.mud.trade.exception.RemoteInvokeException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordLockException;
import com.tony.demo.microservice.mud.trade.service.bean.req.TradeHandleRequest;
import com.tony.demo.microservice.mud.trade.service.bean.req.UserFundOrdersPortfolioDetailRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.UserFundOrdersPortfolioDetailResponse;
import com.tony.demo.microservice.mud.trade.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基金交易基础逻辑
 * <p>
 * Created by Tony on 12/07/2017.
 */
@Service
public class FundTradeBasic {

    private Logger logger = LoggerFactory.getLogger(FundTradeBasic.class);

    // 未支付
    final int UNPAID = -1;
    // 基金公司已受理
    final int ACCEPT = 1;
    // 交易未知错误 人工处理差错
    final int MANUAL = -3;
    // 交易失败
    final int FAILED = 0;

    private final int LOCK_COUNT = 3;
    private final int LOCK_CYCLE = 24 * 60 * 60;
    private final String PWD_LOCK_KEY = "PASSWORD_LOCK_";
    private final String PWD_ERROR_COUNT_KEY = "PASSWORD_ERROR_COUNT_";

    @Autowired
    private FundQueryService fundQueryService;

    @Autowired
    private UserService userService;

    @Autowired
    private HBTradePwdService hbTradePwdService;

    /**
     * 获取用户交易账号信息
     *
     * @param idNumber  用户身份证
     * @param fundCode  基金代码
     * @param bankCarNo 银行账号
     * @return 交易账号信息
     */
    JSONObject getTradeAccountInfo(String idNumber, String fundCode, String bankCarNo) throws Exception {
        return null;
    }

    /**
     * 获取申购交易明细
     *
     * @param idNumber    身份证号
     * @param fundCode    基金代码
     * @param applySerial 交易流水号
     * @return 基金公司交易明细
     */
    Object findRemoteTradeRecord(String idNumber, String fundCode, String applySerial) throws RemoteInvokeException {
        return null;
    }

    /**
     * 交易密码错误3次锁定24小时
     *
     * @param userId 用户id
     * @return
     */
    void lockUser(int userId) throws TradePasswordLockException {

    }

    /**
     * 判断用户交易是否被锁定
     *
     * @param userId 用户id
     * @return
     */
    boolean userIsLocked(int userId) {
        return false;
    }

    /**
     * XX基金平台验证登录(交易密码验证)
     *
     * @param idNumber
     * @param password
     * @throws Exception
     */
    void tryLogin(String idNumber, String password) throws Exception {

    }

    /**
     * 获取用户订单第三方交易的交易信息
     *
     * @param handleRequest
     * @param request
     * @param order
     * @param applySerialStr
     * @throws RemoteInvokeException
     */
    void setRemoteTradeInfo(TradeHandleRequest handleRequest, UserFundOrdersPortfolioDetailRequest request, UserFundOrdersPortfolioDetailResponse order, String applySerialStr) throws RemoteInvokeException {

    }


}
