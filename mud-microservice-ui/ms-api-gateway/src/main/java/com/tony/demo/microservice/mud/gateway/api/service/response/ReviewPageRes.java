package com.tony.demo.microservice.mud.gateway.api.service.response;

import com.wrench.utils.restfulapi.response.PageResponse;

import java.util.List;

public class ReviewPageRes extends PageResponse {

    private List<ReviewRes> list;

    public List<ReviewRes> getList() {
        return list;
    }

    public void setList(List<ReviewRes> list) {
        this.list = list;
    }
}
