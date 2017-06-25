package com.tony.demo.microservice.mud.trade.integration;

import com.tony.demo.microservice.mud.trade.integration.dto.PurchaseResultDTO;
import com.tony.demo.microservice.mud.trade.integration.dto.RedemptionResultDTO;
import org.springframework.stereotype.Service;

/**
 * 交易服务
 * <p>
 * Created by Tony on 11/06/2017.
 */
@Service
public class TradeClient {
    /**
     * 华宝交易申购服务
     */
    String HBXY_TRADE_PURCHASE = "http://mud-microservice-thirdparty/hbxy/trade/purchase";

    public PurchaseResultDTO purchase(String fundCode, String s, String tradeAcco, String s1, String capitalMode, String password, String app_id, String idNumber, String unUsed, String s2, String app_type) {
        return null;
    }

    public RedemptionResultDTO redemption(String fundCode, String s, String tradeAcc, String s1, String password, String app_id, String idNumber, String hbxy, String s2, String app_type, String defaultSaleWay) {
        return null;
    }
}
