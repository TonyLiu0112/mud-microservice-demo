package com.tony.demo.microservice.mud.trade.integration;

import com.tony.demo.microservice.mud.trade.integration.dto.TradeAccountDTO;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeHistoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金信息查询服务
 * <p>
 * Created by Tony on 16/06/2017.
 */
@Service
public class FundQueryClient {

    public List<TradeAccountDTO> getBankInfoForPurchase(String code, String fundCode, String shareType, String idNumber) {
        return null;
    }

    public List<TradeHistoryDTO> queryTradeHistory(String start, String end, String shareType, String fundCode, String idNumber) {
        return null;
    }
}
