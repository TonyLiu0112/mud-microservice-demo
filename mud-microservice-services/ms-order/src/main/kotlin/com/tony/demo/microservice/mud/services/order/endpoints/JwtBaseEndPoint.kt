package com.tony.demo.microservice.mud.services.order.endpoints

import com.tony.demo.microservice.mud.common.session.UserSession
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.stereotype.Component

@Component
class JwtBaseEndPoint {

    @Autowired
    private val defaultTokenServices: DefaultTokenServices? = null

    fun getToken(oAuth2Authentication: OAuth2Authentication): String {
        val details = oAuth2Authentication.details as OAuth2AuthenticationDetails
        return details.tokenValue
    }

    fun getUser(oAuth2Authentication: OAuth2Authentication): UserSession {
        val additionalInformation = defaultTokenServices!!.readAccessToken(getToken(oAuth2Authentication)).additionalInformation
        val objectMapper = ObjectMapper()
        if (additionalInformation["user"] == null || additionalInformation["user"] !is Map<*, *>)
            throw UnsupportedOperationException("Failed to extract user info from token.")
        return objectMapper.convertValue(additionalInformation["user"], UserSession::class.java)
    }
}