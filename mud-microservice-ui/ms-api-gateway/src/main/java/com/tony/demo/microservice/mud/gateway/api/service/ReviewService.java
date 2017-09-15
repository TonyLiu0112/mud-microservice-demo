package com.tony.demo.microservice.mud.gateway.api.service;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.gateway.api.integration.ReviewClient;
import com.tony.demo.microservice.mud.gateway.api.service.response.ReviewRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewClient reviewClient;

    public ReviewService(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    /**
     * 查询评论列表
     *
     * @param productId 产品ID
     * @return 评论列表
     * @throws Exception 业务异常
     */
    public Optional<PageInfo<ReviewRes>> getReviews(long productId, Integer pageNum) throws Exception {
        ResponseEntity<RestfulResponse<PageInfo<ReviewRes>>> responseEntity = reviewClient.getReviews(productId, pageNum);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PageInfo<ReviewRes> pageInfo = responseEntity.getBody().getData();
            if (pageInfo.getList() != null && pageInfo.getList().size() > 0)
                return Optional.of(pageInfo);
        }
        return Optional.empty();
    }

}
