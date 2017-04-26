package com.tony.demo.microservice.mud.customer.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_wechat_config")
public class CustomerWechatConfigDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long customerId;

    private String appid;

    private String appsecret;

    private String accessToken;

    private Date tokenTimeout;

    private Date createTime;

    private String jsapiTicket;

    private Date ticketTimeout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret == null ? null : appsecret.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Date getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(Date tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket == null ? null : jsapiTicket.trim();
    }

    public Date getTicketTimeout() {
        return ticketTimeout;
    }

    public void setTicketTimeout(Date ticketTimeout) {
        this.ticketTimeout = ticketTimeout;
    }
}