package com.tony.demo.microservice.mud.gateway.api.service.request;

public class ShoppingcardReq {

    private Long id;

    private Long productId;

    private Long userId;

    private Integer amount;

    private Integer opsType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getOpsType() {
        return opsType;
    }

    public void setOpsType(Integer opsType) {
        this.opsType = opsType;
    }
}
