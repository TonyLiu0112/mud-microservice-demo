package com.tony.demo.microservice.mud.gateway.api.endpoints;

import com.tony.demo.microservice.mud.gateway.api.service.AuthService;
import com.tony.demo.microservice.mud.gateway.api.service.request.AuthReq;
import com.tony.demo.microservice.mud.gateway.api.service.response.AuthRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
@RequestMapping("security")
public class SecurityEndpoint extends JwtBaseEndpoint {

    private Logger logger = LoggerFactory.getLogger(SecurityEndpoint.class);

    private final AuthService authService;

    public SecurityEndpoint(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("tokens")
    public ResponseEntity<RestfulResponse> fetchToken(@RequestBody AuthReq authReq) {
        try {
            Optional<AuthRes> optional = authService.getToken(authReq);
            if (optional.isPresent())
                return created(optional.get());
            return notImplemented();
        } catch (Exception e) {
            logger.error("Failed to fetch user token.", e);
            return serverError();
        }
    }

}
