package com.tony.demo.microservice.mud.security.api.conf.security;

import com.tony.demo.microservice.mud.common.session.UserSession;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

/**
 * 自定义额外的token信息
 * Created by Tony on 16/04/2017.
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));
        Map<String, String> requestParameters = authentication.getOAuth2Request().getRequestParameters();
        additionalInfo.put("user", new UserSession(
                Long.valueOf(requestParameters.get("user_id")),
                requestParameters.get("user_name"),
                requestParameters.get("nickname"),
                requestParameters.get("phone"),
                requestParameters.get("email"),
                Integer.parseInt(requestParameters.get("sex")),
                requestParameters.get("roles")));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
