package com.tony.demo.microservice.mud.gateway.api.service.response;

import java.util.List;

/**
 * 购物浏览
 */
public class ShoppingViewRes {

    /**
     * 产品信息
     */
    private ProductRes productRes;

    /**
     * 库存信息
     */
    private InventoryRes inventoryRes;

    /**
     * 推荐列表
     */
    private List<RecommendationRes> recommendationResList;

    /**
     * 评论列表
     */
    private List<ReviewRes> reviewResList;

    private List<ShoppingcardRes> shoppingcardList;

    public ProductRes getProductRes() {
        return productRes;
    }

    public void setProductRes(ProductRes productRes) {
        this.productRes = productRes;
    }

    public InventoryRes getInventoryRes() {
        return inventoryRes;
    }

    public void setInventoryRes(InventoryRes inventoryRes) {
        this.inventoryRes = inventoryRes;
    }

    public List<RecommendationRes> getRecommendationResList() {
        return recommendationResList;
    }

    public void setRecommendationResList(List<RecommendationRes> recommendationResList) {
        this.recommendationResList = recommendationResList;
    }

    public List<ReviewRes> getReviewResList() {
        return reviewResList;
    }

    public void setReviewResList(List<ReviewRes> reviewResList) {
        this.reviewResList = reviewResList;
    }

    public List<ShoppingcardRes> getShoppingcardList() {
        return shoppingcardList;
    }

    public void setShoppingcardList(List<ShoppingcardRes> shoppingcardList) {
        this.shoppingcardList = shoppingcardList;
    }
}
