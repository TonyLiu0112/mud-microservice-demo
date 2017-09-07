package com.tony.demo.microservice.mud.services.product.controller;

import com.tony.demo.microservice.mud.services.product.service.ProductService;
import com.tony.demo.microservice.mud.services.product.service.bean.response.ProductRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.*;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products/{id}")
    public ResponseEntity<RestfulResponse> getProduct(@PathVariable("id") Long id) {
        try {
            Optional<ProductRes> optional = productService.findById(id);
            if (optional.isPresent())
                return ok(optional.get());
            return notFound();
        } catch (Exception e) {
            return serverError();
        }
    }

//    @GetMapping("products/{ids}")
//    public ResponseEntity<RestfulResponse> getProducts(@PathVariable("ids") Long[] id) {
//        try {
//            Optional<List<ProductRes>> optional = productService.findByIds(id);
//            if (optional.isPresent())
//                return ok(optional.get());
//            return notFound();
//        } catch (Exception e) {
//            return serverError();
//        }
//    }

}
