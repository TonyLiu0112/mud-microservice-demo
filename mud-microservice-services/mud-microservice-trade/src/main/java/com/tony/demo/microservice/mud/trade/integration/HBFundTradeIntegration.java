package com.tony.demo.microservice.mud.trade.integration;

import com.tony.demo.microservice.mud.trade.service.bean.res.PurchaseResponseBean;
import com.tony.demo.microservice.mud.trade.service.bean.res.SaleResponseBean;
import org.springframework.stereotype.Service;

/**
 * Created by Tony on 18/07/2017.
 */
@Service
public class HBFundTradeIntegration {

    public PurchaseResponseBean purchase(String fundCode, String s, String tradeAcco, String s1, String capitalMode, String password, String app_id, String idNumber, String unUsed, String s2, String app_type) {
        return null;
    }

    public SaleResponseBean sale(String fundCode, String s, String tradeAcco, String s1, String password, String app_id, String idNumber, String s2, String s3, String app_type, String defaultSaleWay) {
        return null;
    }
}
