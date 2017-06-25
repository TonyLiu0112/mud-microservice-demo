package com.tony.demo.microservice.mud.trade.service;

import com.kiisoo.tp.common.redis3.RedisClient;
import com.tony.demo.microservice.mud.trade.exception.RemoteInvokeException;
import com.tony.demo.microservice.mud.trade.exception.TradePasswordLockException;
import com.tony.demo.microservice.mud.trade.integration.FundQueryClient;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeAccountDTO;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeHistoryDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 基金交易基础逻辑
 * <p>
 * Created by Tony on 12/07/2017.
 */
@Service
public class FundTradeBasic {

    private Logger logger = LoggerFactory.getLogger(FundTradeBasic.class);

    final String PASSWORD_ERROR = "0021"; //"交易密码不正确";

    final int ACCEPT = 1;
    final int APPLYING = -3;

    private final int LOCK_CYCLE = 24 * 60 * 60;
    private final String PWD_LOCK_KEY = "PASSWORD_LOCK_";
    private final String PWD_ERROR_COUNT_KEY = "PASSWORD_ERROR_COUNT_";

    @Autowired
    private FundQueryClient fundQueryService;

    /**
     * 获取用户交易账号信息
     *
     * @param idNumber 用户身份证
     * @param fundCode 基金代码
     * @param bankCode 银行代码
     * @return 交易账号信息
     */
    TradeAccountDTO getTradeAccountInfo(String idNumber, String fundCode, String bankCode) throws Exception {
        List<TradeAccountDTO> accountDTOList = fundQueryService.getBankInfoForPurchase("", fundCode, "", idNumber);
        Optional<TradeAccountDTO> first = accountDTOList.stream().filter(tradeAccountDTO -> bankCode.equals(tradeAccountDTO.getBankSerial())).findFirst();
        return first.orElse(null);
    }

    /**
     * 获取申购交易明细
     *
     * @param idNumber    身份证号
     * @param fundCode    基金代码
     * @param applySerial 交易流水号
     * @return 基金公司交易明细
     */
    TradeHistoryDTO findRemoteTradeRecord(String idNumber, String fundCode, String applySerial) throws RemoteInvokeException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.add(Calendar.DAY_OF_MONTH, -1);
        String start = sdf.format(now.getTime());
        now.add(Calendar.DAY_OF_MONTH, 2);
        String end = sdf.format(now.getTime());
        try {
            List<TradeHistoryDTO> tradeHistoryDTOList = fundQueryService.queryTradeHistory(start, end, "", fundCode, idNumber);
            Optional<TradeHistoryDTO> first = tradeHistoryDTOList.stream().filter(tradeHistoryDTO -> tradeHistoryDTO.getApplySerial().equals(applySerial)).findFirst();
            return first.orElseThrow(RemoteInvokeException::new);
        } catch (Exception e) {
            logger.error("获取华宝交易明细数据失败.", e);
            throw e;
        }
    }

    /**
     * 超过3次锁定24小时
     *
     * @param userId 用户id
     * @return
     */
    void lockUser(int userId) throws TradePasswordLockException {
        if (!RedisClient.exists(PWD_LOCK_KEY + userId)) {
            String count = RedisClient.get(PWD_ERROR_COUNT_KEY + userId);
            if (StringUtils.isNotBlank(count)) {
                int cnt = Integer.parseInt(count) + 1;
                RedisClient.set(PWD_ERROR_COUNT_KEY + userId, "" + cnt);
                RedisClient.expire(PWD_ERROR_COUNT_KEY + userId, LOCK_CYCLE);
                if (cnt >= 3) {
                    RedisClient.set(PWD_LOCK_KEY + userId, "Locked");
                    RedisClient.expire(PWD_LOCK_KEY + userId, LOCK_CYCLE);
                    throw new TradePasswordLockException();
                }
            } else {
                RedisClient.set(PWD_ERROR_COUNT_KEY + userId, "1");
                RedisClient.expire(PWD_ERROR_COUNT_KEY + userId, LOCK_CYCLE);
            }
        }
    }

    /**
     * 判断用户交易是否被锁定
     *
     * @param userId 用户id
     * @return
     */
    boolean userIsLocked(int userId) {
        return RedisClient.exists(PWD_LOCK_KEY + userId);
    }


}
