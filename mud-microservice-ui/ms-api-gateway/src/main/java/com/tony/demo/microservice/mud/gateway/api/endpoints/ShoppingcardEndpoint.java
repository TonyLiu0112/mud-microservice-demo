package com.tony.demo.microservice.mud.gateway.api.endpoints;


import com.tony.demo.microservice.mud.common.session.UserSession;
import com.tony.demo.microservice.mud.gateway.api.service.ShoppingcardService;
import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("shoppingcard")
public class ShoppingcardEndpoint extends JwtBaseEndpoint {

    private final ShoppingcardService shoppingcardService;

    public ShoppingcardEndpoint(ShoppingcardService shoppingcardService) {
        this.shoppingcardService = shoppingcardService;
    }

    /**
     * 查询购物车列表
     *
     * @param oAuth2Authentication
     * @return
     */
    @GetMapping("shoppingcards")
    public ResponseEntity<RestfulResponse> getShoppingcard(OAuth2Authentication oAuth2Authentication) {
        try {
            UserSession user = getUser(oAuth2Authentication);
            return RestfulBuilder.ok(shoppingcardService.getShoppingcards(user.getId()).orElseGet(ArrayList::new));
        } catch (Exception e) {
            return RestfulBuilder.serverError();
        }
    }

}
