package com.tony.demo.microservice.mud.web.manager.controller;

import com.tony.demo.microservice.mud.web.manager.service.ProductClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductClient productClient;

    public ProductController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("{id}")
    public Object getProduct(@PathVariable("id") long id) {
        try {
            return productClient.getProduct(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
