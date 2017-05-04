package com.tony.demo.microservice.mud.web.manager.conf;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.util.Assert;

public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2FeignRequestInterceptor.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final OAuth2ClientContext oauth2ClientContext;

    public OAuth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext) {
        Assert.notNull(oauth2ClientContext, "Context can not be null");
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
            LOGGER.warn("The Authorization token has been already set");
        } else if (StringUtil.isBlank(oauth2ClientContext.getAccessToken().getValue())) {
            LOGGER.warn("Can not obtain existing token for request, if it is a non secured request, ignore.");
        } else {
            LOGGER.debug("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE,
                    oauth2ClientContext.getAccessToken().getValue()));
        }
    }

}
