package com.tony.demo.microservice.mud.services.shoppingcard.controller;

import com.tony.demo.microservice.mud.services.shoppingcard.service.ShoppingcardService;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.request.ShoppingcardReq;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ShoppingcardRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
public class ShoppingcardController {

    private Logger logger = LoggerFactory.getLogger(ShoppingcardController.class);

    private final ShoppingcardService shoppingcardService;

    public ShoppingcardController(ShoppingcardService shoppingcardService) {
        this.shoppingcardService = shoppingcardService;
    }

    @GetMapping("shoppingcards")
    public ResponseEntity<RestfulResponse> getShoppingcard(@RequestParam("userId") Long userId) {
        try {
            Optional<List<ShoppingcardRes>> optional = shoppingcardService.getShoppingcards(userId);
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

}
