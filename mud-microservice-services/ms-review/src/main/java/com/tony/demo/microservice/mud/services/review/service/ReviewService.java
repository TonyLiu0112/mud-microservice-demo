package com.tony.demo.microservice.mud.services.review.service;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.review.dao.entity.ReviewDO;
import com.tony.demo.microservice.mud.services.review.dao.repository.ReviewRepository;
import com.tony.demo.microservice.mud.services.review.service.bean.response.ReviewRes;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Optional<PageInfo<ReviewRes>> getReviews(long productId, Integer pageNum) throws Exception {
        Page<ReviewDO> page = reviewRepository.findByProductId(productId, new PageRequest(pageNum - 1, 10));
        if (page == null || page.getContent().size() == 0)
            return Optional.empty();
        Page<ReviewRes> resPage = page.map(source -> {
            ReviewRes res = new ReviewRes();
            BeanUtils.copyProperties(source, res);
            // TODO: 20/08/2017 find user info
            return res;
        });
        return Optional.of(new PageInfo<>(ConvertUtils.convert(resPage)));
    }

}
