package com.tony.demo.microservice.mud.gateway.api.service;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.gateway.api.integration.ProductClient;
import com.tony.demo.microservice.mud.gateway.api.service.response.ProductPageRes;
import com.tony.demo.microservice.mud.gateway.api.service.response.ProductRes;
import com.wrench.utils.restfulapi.helper.ExtractUtil;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Optional<PageInfo<ProductRes>> getProducts(String name, int pageNum) throws Exception {
        ResponseEntity<RestfulResponse<PageInfo<ProductRes>>> responseEntity = productClient.getProducts(name, pageNum);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            PageInfo<ProductRes> pageInfo = responseEntity.getBody().getData();
            return Optional.of(pageInfo);
        }
        return Optional.empty();
    }
}
