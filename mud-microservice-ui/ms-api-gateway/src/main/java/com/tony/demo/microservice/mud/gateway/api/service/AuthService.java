package com.tony.demo.microservice.mud.gateway.api.service;

import com.tony.demo.microservice.mud.gateway.api.integration.AuthClient;
import com.tony.demo.microservice.mud.gateway.api.service.request.AuthReq;
import com.tony.demo.microservice.mud.gateway.api.service.response.AuthRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthClient authClient;


    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public Optional<AuthRes> getToken(AuthReq authReq) throws Exception {
        authReq.setClientId("acme");
        authReq.setSecret("acmesecret");
        ResponseEntity<RestfulResponse<AuthRes>> responseEntity = authClient.getToken(authReq);
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            RestfulResponse<AuthRes> body = responseEntity.getBody();
            return Optional.of(body.getData());
        }
        return Optional.empty();
    }
}
