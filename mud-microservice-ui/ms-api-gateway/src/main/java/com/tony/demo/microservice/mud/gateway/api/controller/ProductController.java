package com.tony.demo.microservice.mud.gateway.api.controller;

import com.tony.demo.microservice.mud.common.session.UserSession;
import com.tony.demo.microservice.mud.gateway.api.service.ShoppingService;
import com.tony.demo.microservice.mud.gateway.api.service.response.ShoppingViewRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.ok;
import static com.wrench.utils.restfulapi.response.RestfulBuilder.serverError;

@RestController
@RequestMapping("shelf")
public class ProductController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ShoppingService shoppingService;

    public ProductController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    /**
     * 产品明细（推荐列表、评论列表、产品基本信息）
     * @param pId
     * @param oAuth2Authentication
     * @return
     */
    @GetMapping("products/{pId}")
    public ResponseEntity<RestfulResponse> getProduct(@PathVariable("pId") Long pId, OAuth2Authentication oAuth2Authentication) {
        try {
            UserSession userSession = getUser(oAuth2Authentication);
            Optional<ShoppingViewRes> shoppingProductInfo = shoppingService.getShoppingProductInfo(userSession.getId(), pId);
            return ok(shoppingProductInfo.orElse(new ShoppingViewRes()));
        } catch (Exception e) {
            logger.error("获取商品信息异常.", e);
            return serverError();
        }
    }

}