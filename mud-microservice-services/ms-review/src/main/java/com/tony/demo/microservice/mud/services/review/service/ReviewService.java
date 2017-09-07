package com.tony.demo.microservice.mud.services.review.service;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.review.dao.entity.ReviewDO;
import com.tony.demo.microservice.mud.services.review.dao.repository.ReviewRepository;
import com.tony.demo.microservice.mud.services.review.service.bean.response.ReviewRes;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Optional<List<ReviewRes>> getReviews(long productId) throws Exception {
        List<ReviewDO> records = reviewRepository.findByProductId(productId);
        if (records == null || records.size() == 0)
            return Optional.empty();
        List<ReviewRes> resList = ConvertUtils.convert(records, source -> {
            ReviewRes result = new ReviewRes();
            BeanUtils.copyProperties(source, result);
            // TODO: 20/08/2017 find user info
            return result;
        });
        return Optional.of(resList);
    }

}
