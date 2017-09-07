package com.tony.demo.microservice.mud.gateway.api.controller;

import com.tony.demo.microservice.mud.common.session.UserSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseController {

    @Autowired
    private DefaultTokenServices defaultTokenServices;

    public String getToken(OAuth2Authentication oAuth2Authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();
        return details.getTokenValue();
    }

    public UserSession getUser(OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInformation = defaultTokenServices.readAccessToken(getToken(oAuth2Authentication)).getAdditionalInformation();
        ObjectMapper objectMapper = new ObjectMapper();
        if (additionalInformation.get("user") == null || !(additionalInformation.get("user") instanceof Map))
            throw new UnsupportedOperationException("Failed to extract user info from token.");
        return objectMapper.convertValue(additionalInformation.get("user"), UserSession.class);
    }

}
