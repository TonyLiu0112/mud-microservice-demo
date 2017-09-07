package com.tony.demo.microservice.mud.security.api.controller;

import com.tony.demo.microservice.mud.common.AbstractController;
import com.tony.demo.microservice.mud.common.content.code.APICode;
import com.tony.demo.microservice.mud.security.api.service.AuthenticationService;
import com.tony.demo.microservice.mud.security.api.service.bean.SecurityUserReq;
import com.tony.demo.microservice.mud.security.api.service.bean.UserWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * 安全性相关控制器
 * <p>
 * Created by Tony on 27/02/2017.
 */
@RestController
@RequestMapping("security")
public class SecurityController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public SecurityController(AuthenticationService authenticationService, TokenStore tokenStore) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("doLogin")
    public Map<String, Object> doLogin(@RequestBody SecurityUserReq userReq) {
        try {
            Optional<UserWrapper> optional = authenticationService.doAuthentication(userReq);
            if (optional.isPresent()) {
                return data(authenticationService.obtainJwtToken(userReq.getClientId(), userReq.getSecret(), optional.get()));
            }
        } catch (Exception e) {
            logger.error("Authenticate error.", e);
        }
        return fail(APICode.AUTHENTICATE_ERROR);
    }

}
