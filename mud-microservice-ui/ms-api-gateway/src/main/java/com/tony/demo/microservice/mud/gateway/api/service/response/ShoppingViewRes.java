package com.tony.demo.microservice.mud.gateway.api.service.response;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 购物浏览
 */
public class ShoppingViewRes {

    /**
     * 产品信息
     */
    private ProductRes product;

    /**
     * 库存信息
     */
    private InventoryRes inventory;

    /**
     * 推荐列表
     */
    private List<RecommendationRes> recommendations;

    /**
     * 评论列表
     */
    private PageInfo<ReviewRes> reviews;

    public ProductRes getProduct() {
        return product;
    }

    public void setProduct(ProductRes product) {
        this.product = product;
    }

    public InventoryRes getInventory() {
        return inventory;
    }

    public void setInventory(InventoryRes inventory) {
        this.inventory = inventory;
    }

    public List<RecommendationRes> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationRes> recommendations) {
        this.recommendations = recommendations;
    }

    public PageInfo<ReviewRes> getReviews() {
        return reviews;
    }

    public void setReviews(PageInfo<ReviewRes> reviews) {
        this.reviews = reviews;
    }
}
