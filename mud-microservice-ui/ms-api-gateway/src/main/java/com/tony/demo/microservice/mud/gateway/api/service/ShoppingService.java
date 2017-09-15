package com.tony.demo.microservice.mud.gateway.api.service;

import com.tony.demo.microservice.mud.gateway.api.integration.InventoryClient;
import com.tony.demo.microservice.mud.gateway.api.integration.ProductClient;
import com.tony.demo.microservice.mud.gateway.api.integration.RecommendationClient;
import com.tony.demo.microservice.mud.gateway.api.service.response.InventoryRes;
import com.tony.demo.microservice.mud.gateway.api.service.response.ProductRes;
import com.tony.demo.microservice.mud.gateway.api.service.response.RecommendationRes;
import com.tony.demo.microservice.mud.gateway.api.service.response.ShoppingViewRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingService {

    private Logger logger = LoggerFactory.getLogger(ShoppingService.class);

    private final RecommendationClient recommendationClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final ReviewService reviewService;

    public ShoppingService(RecommendationClient recommendationClient, ProductClient productClient, InventoryClient inventoryClient, ReviewService reviewService) {
        this.recommendationClient = recommendationClient;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
        this.reviewService = reviewService;
    }

    /**
     * 获取商品产品信息
     * 包括推荐、评论、产品明细、库存信息
     *
     * @return 产品相关所有信息集
     * @throws Exception 业务异常
     */
    public Optional<ShoppingViewRes> getShoppingProductInfo(long productId) throws Exception {
        ShoppingViewRes shoppingViewRes = new ShoppingViewRes();
        // 查询产品信息
        Optional<ProductRes> productResOptional = getProduct(productId);
        if (productResOptional.isPresent()) {
            ProductRes productRes = productResOptional.get();
            shoppingViewRes.setProduct(productRes);
            // 查询推荐信息
            getRecommendations(productRes.getTag()).ifPresent(shoppingViewRes::setRecommendations);
            // 查询库存信息
            getInventory(productId).ifPresent(shoppingViewRes::setInventory);
        }
        reviewService.getReviews(productId, 1).ifPresent(shoppingViewRes::setReviews);
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
        ResponseEntity<RestfulResponse<ProductRes>> responseEntity = productClient.getProduct(productId);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ProductRes productRes = responseEntity.getBody().getData();
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
        ResponseEntity<RestfulResponse<InventoryRes>> responseEntity = inventoryClient.getInventory(productId);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            InventoryRes inventoryRes = responseEntity.getBody().getData();
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
        ResponseEntity<RestfulResponse<List<RecommendationRes>>> responseEntity = recommendationClient.recommendations(tag);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<RecommendationRes> recommendationRes = responseEntity.getBody().getData();
            recommendationRes.forEach(recPro -> {
                try {
                    Optional<ProductRes> product = getProduct(recPro.getProductId());
                    product.ifPresent(recPro::setProduct);
                } catch (Exception e) {
                    logger.error("Failed to query product info for recommendations.", e);
                }
            });
            return Optional.of(recommendationRes);
        }
        return Optional.empty();
    }

}
