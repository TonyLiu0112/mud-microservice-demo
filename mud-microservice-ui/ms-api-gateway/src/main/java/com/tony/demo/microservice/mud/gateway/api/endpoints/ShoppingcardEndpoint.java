package com.tony.demo.microservice.mud.gateway.api.endpoints;


import com.tony.demo.microservice.mud.common.session.UserSession;
import com.tony.demo.microservice.mud.gateway.api.service.ShoppingcardService;
import com.tony.demo.microservice.mud.gateway.api.service.request.ShoppingcardReq;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
@RequestMapping("shoppingcard")
public class ShoppingcardEndpoint extends JwtBaseEndpoint {

    private Logger logger = LoggerFactory.getLogger(ShoppingcardEndpoint.class);

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
            return ok(shoppingcardService.getShoppingcards(user.getId()).orElseGet(ArrayList::new));
        } catch (Exception e) {
            return serverError();
        }
    }

    /**
     * 添加购物车信息
     *
     * @param shoppingcardReq
     * @param oAuth2Authentication
     * @return
     */
    @PostMapping("shoppingcards")
    public ResponseEntity<RestfulResponse> addShoppingcard(@RequestBody ShoppingcardReq shoppingcardReq, OAuth2Authentication oAuth2Authentication) {
        try {
            UserSession user = getUser(oAuth2Authentication);
            shoppingcardReq.setId(user.getId());
            Optional<Long> optional = shoppingcardService.addShoppingcard(shoppingcardReq);
            return created(optional.orElse(0L));
        } catch (Exception e) {
            logger.error("Failed to add product to shopping card.", e);
            return serverError();
        }
    }

}
