package com.tony.demo.microservice.mud.gateway.api.endpoints;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.gateway.api.service.ProductService;
import com.tony.demo.microservice.mud.gateway.api.service.ShoppingService;
import com.tony.demo.microservice.mud.gateway.api.service.response.ShoppingViewRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.wrench.utils.restfulapi.response.RestfulBuilder.ok;
import static com.wrench.utils.restfulapi.response.RestfulBuilder.serverError;

@RestController
@RequestMapping("shelf")
public class ProductEndpoint extends JwtBaseEndpoint {

    private Logger logger = LoggerFactory.getLogger(ProductEndpoint.class);

    private final ShoppingService shoppingService;
    private final ProductService productService;

    public ProductEndpoint(ShoppingService shoppingService, ProductService productService) {
        this.shoppingService = shoppingService;
        this.productService = productService;
    }

    /**
     * 产品明细（推荐列表、评论列表、产品基本信息）
     *
     * @param pId
     * @return
     */
    @GetMapping("products/{pId}")
    public ResponseEntity<RestfulResponse> getProduct(@PathVariable("pId") Long pId) {
        try {
            Optional<ShoppingViewRes> shoppingProductInfo = shoppingService.getShoppingProductInfo(pId);
            return ok(shoppingProductInfo.orElse(new ShoppingViewRes()));
        } catch (Exception e) {
            logger.error("获取商品信息异常.", e);
            return serverError();
        }
    }

    /**
     * 产品搜索查询
     *
     * @param name
     * @param pageNum
     * @return
     */
    @GetMapping("find/products/{name}")
    public ResponseEntity<RestfulResponse> findProducts(@PathVariable("name") String name,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        try {
            return ok(productService.getProducts(name, pageNum).orElse(new PageInfo<>()));
        } catch (Exception e) {
            logger.error("搜索产品信息异常.", e);
            return serverError();
        }
    }

}