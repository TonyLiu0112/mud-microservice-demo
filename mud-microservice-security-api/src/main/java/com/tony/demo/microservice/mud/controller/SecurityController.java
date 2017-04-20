package com.tony.demo.microservice.mud.controller;

import com.tony.demo.microservice.mud.AbstractController;
import com.tony.demo.microservice.mud.content.code.APICode;
import com.tony.demo.microservice.mud.service.AuthenticationService;
import com.tony.demo.microservice.mud.service.dto.SecurityUserReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
            if (authenticationService.doAuthentication(userReq)) {
                return data(authenticationService.obtainJwtToken(userReq.getClientId(), userReq.getSecret()));
            }
        } catch (Exception e) {
            logger.error("Authenticate error.", e);
        }
        return fail(APICode.AUTHENTICATE_ERROR);
    }

}
