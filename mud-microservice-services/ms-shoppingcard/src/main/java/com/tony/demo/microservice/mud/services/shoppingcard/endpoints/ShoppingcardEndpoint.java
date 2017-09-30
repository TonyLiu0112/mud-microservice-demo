package com.tony.demo.microservice.mud.services.shoppingcard.endpoints;

import com.tony.demo.microservice.mud.common.session.UserSession;
import com.tony.demo.microservice.mud.services.shoppingcard.service.ShoppingcardService;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.request.ShoppingcardReq;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ShoppingcardRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
public class ShoppingcardEndpoint extends JwtBaseEndpoint {

    private Logger logger = LoggerFactory.getLogger(ShoppingcardEndpoint.class);

    private final ShoppingcardService shoppingcardService;

    public ShoppingcardEndpoint(ShoppingcardService shoppingcardService) {
        this.shoppingcardService = shoppingcardService;
    }

    @GetMapping("shoppingcards")
    public ResponseEntity<RestfulResponse> getShoppingcard(OAuth2Authentication oAuth2Authentication) {
        try {
            UserSession user = getUser(oAuth2Authentication);
            Optional<List<ShoppingcardRes>> optional = shoppingcardService.getShoppingcards(user.getId());
            if (optional.isPresent())
                return ok(optional.get());
            return notFound();
        } catch (Exception e) {
            logger.error("Failed to get shopping card information.", e);
            return serverError();
        }
    }

    @PostMapping("shoppingcards")
    public ResponseEntity<RestfulResponse> storeShoppingcard(@RequestBody ShoppingcardReq shoppingcardReq) {
        try {
            Optional<Long> optional = shoppingcardService.addShoppingcard(shoppingcardReq);
            if (optional.isPresent())
                return created(optional.get());
            return notImplemented();
        } catch (Exception e) {
            logger.error("Failed to storage product info shopping card.", e);
            return serverError();
        }
    }

    @DeleteMapping("shoppingcards")
    public ResponseEntity<RestfulResponse> removeShoppingcard(@RequestBody ShoppingcardReq shoppingcardReq) {
        try {
            shoppingcardService.removeProduct(shoppingcardReq);
            return deleted();
        } catch (Exception e) {
            logger.error("Failed to delete product from shopping card.", e);
            return serverError();
        }
    }

}
