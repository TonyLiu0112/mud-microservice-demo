package com.tony.demo.microservice.mud.customer.services.model.req;

/**
 * Created by Tony on 27/02/2017.
 */
public class SecurityUserReq {

    private String loginName;

    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
