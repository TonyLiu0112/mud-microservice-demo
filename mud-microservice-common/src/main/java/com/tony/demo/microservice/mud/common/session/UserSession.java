package com.tony.demo.microservice.mud.common.session;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

public class UserSession implements Serializable {

    private Long id;

    private String loginName;

    private String nickname;

    private String phone;

    private String email;

    private Integer sex;

    private List<String> roles;

    public UserSession() {
    }

    public UserSession(Long id, String loginName, String nickname, String phone, String email, Integer sex) {
        this.id = id;
        this.loginName = loginName;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, true);
    }
}