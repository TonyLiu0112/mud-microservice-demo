package com.tony.demo.microservice.mud.gateway.api.service;

import com.tony.demo.microservice.mud.gateway.api.integration.ShoppingcardClient;
import com.tony.demo.microservice.mud.gateway.api.service.request.ShoppingcardReq;
import com.tony.demo.microservice.mud.gateway.api.service.response.ShoppingcardRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingcardService {

    private final ShoppingcardClient shoppingcardClient;

    public ShoppingcardService(ShoppingcardClient shoppingcardClient) {
        this.shoppingcardClient = shoppingcardClient;
    }

    /**
     * 查询购物车信息
     *
     * @param userId 用户ID
     * @return 购物车信息列表
     * @throws Exception 业务异常
     */
    public Optional<List<ShoppingcardRes>> getShoppingcards(long userId) throws Exception {
        ResponseEntity<RestfulResponse<List<ShoppingcardRes>>> responseEntity = shoppingcardClient.getShoppingcard(userId);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<ShoppingcardRes> shoppingcardRes = responseEntity.getBody().getData();
            return Optional.of(shoppingcardRes);
        }
        return Optional.empty();
    }

    public Optional<Long> addShoppingcard(ShoppingcardReq shoppingcardReq) throws Exception {
        ResponseEntity<RestfulResponse<Long>> responseEntity = shoppingcardClient.storeShoppingcard(shoppingcardReq);
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            return Optional.of(responseEntity.getBody().getData());
        }
        return Optional.empty();
    }

    public Optional<Boolean> removeShoppingcard(ShoppingcardReq shoppingcardReq) throws Exception {
        ResponseEntity<RestfulResponse> responseEntity = shoppingcardClient.removeShoppingcard(shoppingcardReq);
        if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            return Optional.of(true);
        }
        return Optional.of(false);
    }
}
