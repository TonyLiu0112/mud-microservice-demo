package com.tony.demo.microservice.mud.trade.service.bean.req;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 26/06/2017.
 */
public class TradeHandleRequest {

    private String password;

    private List<String> orders;

    private String tradeAcco;

    /**
     * 资金模式
     */
    private String capitalMode;

    private String idNumber;

    /**
     * 交易类型
     * 1 申购
     * 2 赎回
     */
    private int type;

    public TradeHandleRequest(int type) {
        orders = new ArrayList<>();
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public String getTradeAcco() {
        return tradeAcco;
    }

    public void setTradeAcco(String tradeAcco) {
        this.tradeAcco = tradeAcco;
    }

    public String getCapitalMode() {
        return capitalMode;
    }

    public void setCapitalMode(String capitalMode) {
        this.capitalMode = capitalMode;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void addOrder(String orderId) {
        this.orders.add(orderId);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
