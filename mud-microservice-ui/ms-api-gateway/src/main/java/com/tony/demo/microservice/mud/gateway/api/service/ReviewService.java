package com.tony.demo.microservice.mud.gateway.api.service;

import com.tony.demo.microservice.mud.gateway.api.integration.ReviewClient;
import com.tony.demo.microservice.mud.gateway.api.service.response.ReviewPageRes;
import com.wrench.utils.restfulapi.helper.ExtractUtil;
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
    public Optional<ReviewPageRes> getReviews(long productId, Integer pageNum) throws Exception {
        ResponseEntity<RestfulResponse> responseEntity = reviewClient.getReviews(productId, pageNum);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            ReviewPageRes resPage = ExtractUtil.extractData(responseEntity, ReviewPageRes.class);
            return Optional.of(resPage);
        }
        return Optional.empty();
    }

}
