package com.tony.demo.microservice.mud.services.shoppingcard.controller;

import com.tony.demo.microservice.mud.services.shoppingcard.service.ShoppingcardService;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ShoppingcardRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
public class ShoppingcardController {

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
            return serverError();
        }
    }

}
