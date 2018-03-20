package com.tony.demo.microservice.mud.security.api.endpoints;

import com.tony.demo.microservice.mud.common.content.code.APICode;
import com.tony.demo.microservice.mud.security.api.service.AuthenticationService;
import com.tony.demo.microservice.mud.security.api.service.bean.SecurityUserReq;
import com.tony.demo.microservice.mud.security.api.service.bean.UserWrapper;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 安全性相关控制器
 * <p>
 * Created by Tony on 27/02/2017.
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    private Logger logger = LoggerFactory.getLogger(SecurityController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public SecurityController(AuthenticationService authenticationService, TokenStore tokenStore) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("doLogin")
    public ResponseEntity<RestfulResponse> doLogin(@RequestBody SecurityUserReq userReq) {
        try {
            Optional<UserWrapper> optional = authenticationService.doAuthentication(userReq);
            if (optional.isPresent())
                return RestfulBuilder.created(authenticationService.obtainJwtToken(userReq.getClientId(), userReq.getSecret(), optional.get()));
            return RestfulBuilder.notImplemented();
        } catch (Exception e) {
            logger.error("Authenticate error.", e);
            return RestfulBuilder.serverError(APICode.AUTHENTICATE_ERROR);
        }
    }

}
