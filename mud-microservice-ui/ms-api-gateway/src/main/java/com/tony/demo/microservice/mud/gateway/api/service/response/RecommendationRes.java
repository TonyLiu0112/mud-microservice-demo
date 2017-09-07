package com.tony.demo.microservice.mud.gateway.api.service.response;

/**
 * 推荐
 */
public class RecommendationRes {

    private Long productId;

    private String tag;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
