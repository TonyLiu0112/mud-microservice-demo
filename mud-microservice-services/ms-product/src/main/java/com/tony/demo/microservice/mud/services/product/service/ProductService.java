package com.tony.demo.microservice.mud.services.product.service;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.product.dao.entity.ProductDO;
import com.tony.demo.microservice.mud.services.product.dao.repository.ProductRepository;
import com.tony.demo.microservice.mud.services.product.service.bean.response.ProductRes;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductRes> findById(Long id) throws Exception {
        ProductDO record = productRepository.findOne(id);
        return Optional.of(ConvertUtils.convert(record, ProductRes.class));
    }

    public Optional<List<ProductRes>> findByIds(Long[] ids) throws Exception {
        if (ids == null || ids.length == 0)
            throw new RuntimeException("Request is empty.");
        if (ids.length > 10)
            throw new RuntimeException("Request is too much more.");
        List<ProductDO> records = productRepository.findByIdIn(Arrays.asList(ids));
        return Optional.of(ConvertUtils.convert(records, ProductRes.class));
    }

}
