package com.tony.demo.microservice.mud.gateway.api.service;

import com.tony.demo.microservice.mud.gateway.api.integration.InventoryClient;
import com.tony.demo.microservice.mud.gateway.api.integration.ProductClient;
import com.tony.demo.microservice.mud.gateway.api.integration.RecommendationClient;
import com.tony.demo.microservice.mud.gateway.api.integration.ShoppingcardClient;
import com.tony.demo.microservice.mud.gateway.api.service.response.*;
import com.wrench.utils.restfulapi.helper.ExtractUtil;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingService {

    private final RecommendationClient recommendationClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public ShoppingService(RecommendationClient recommendationClient, ProductClient productClient, InventoryClient inventoryClient) {
        this.recommendationClient = recommendationClient;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    /**
     * 获取商品产品信息
     * 包括推荐、评论、产品明细、库存信息、购物车信息
     *
     * @param productId 产品ID
     * @return 产品相关所有信息集
     * @throws Exception 业务异常
     */
    public Optional<ShoppingViewRes> getShoppingProductInfo(long userId, long productId) throws Exception {
        ShoppingViewRes shoppingViewRes = new ShoppingViewRes();
        // 查询产品信息
        Optional<ProductRes> productResOptional = getProduct(productId);
        if (productResOptional.isPresent()) {
            ProductRes productRes = productResOptional.get();
            shoppingViewRes.setProductRes(productRes);
            // 查询推荐信息
            getRecommendations(productRes.getTag()).ifPresent(shoppingViewRes::setRecommendationResList);
            // 查询库存信息
            getInventory(productId).ifPresent(shoppingViewRes::setInventoryRes);
        }
        return Optional.of(shoppingViewRes);
    }

    /**
     * 查询产品信息
     *
     * @param productId 产品ID
     * @return 产品基本信息
     * @throws Exception 业务异常
     */
    private Optional<ProductRes> getProduct(long productId) throws Exception {
        ResponseEntity<RestfulResponse> responseEntity = productClient.getProduct(productId);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ProductRes productRes = ExtractUtil.extractData(responseEntity, ProductRes.class);
            return Optional.of(productRes);
        }
        return Optional.empty();
    }

    /**
     * 查询库存信息
     *
     * @param productId 产品ID
     * @return 库存信息
     * @throws Exception 业务异常
     */
    private Optional<InventoryRes> getInventory(long productId) throws Exception {
        ResponseEntity<RestfulResponse> responseEntity = inventoryClient.getInventory(productId);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            InventoryRes inventoryRes = ExtractUtil.extractData(responseEntity, InventoryRes.class);
            return Optional.of(inventoryRes);
        }
        return Optional.empty();
    }

    /**
     * 查询推荐列表
     *
     * @param tag 商品标签
     * @return 推荐列表
     * @throws Exception 业务异常
     */
    private Optional<List<RecommendationRes>> getRecommendations(String tag) throws Exception {
        ResponseEntity<RestfulResponse> responseEntity = recommendationClient.recommendations(tag);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<RecommendationRes> recommendationRes = ExtractUtil.extractList(responseEntity, RecommendationRes.class);
            return Optional.of(recommendationRes);
        }
        return Optional.empty();
    }

}
