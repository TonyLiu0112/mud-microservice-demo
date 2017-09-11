package com.tony.demo.microservice.mud.gateway.api.service.response;

import com.wrench.utils.restfulapi.response.PageResponse;

import java.util.List;

public class ProductPageRes extends PageResponse {

    private List<ProductRes> list;

    public List<ProductRes> getList() {
        return list;
    }

    public void setList(List<ProductRes> list) {
        this.list = list;
    }
}
