package com.tony.demo.microservice.mud.security.api.service.bean;

import com.tony.demo.microservice.mud.common.session.UserSession;
import org.springframework.security.core.userdetails.User;

public class UserWrapper extends UserSession {

    private User user;

    public UserWrapper() {
    }

    public UserWrapper(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
