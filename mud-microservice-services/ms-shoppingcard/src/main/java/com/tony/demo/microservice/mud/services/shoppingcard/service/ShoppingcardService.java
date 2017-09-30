package com.tony.demo.microservice.mud.services.shoppingcard.service;

import com.tony.demo.microservice.mud.services.shoppingcard.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.shoppingcard.dao.entity.ShoppingcardDO;
import com.tony.demo.microservice.mud.services.shoppingcard.dao.repository.ShoppingcardRepository;
import com.tony.demo.microservice.mud.services.shoppingcard.integration.ProductClient;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.request.ShoppingcardReq;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ProductRes;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ShoppingcardRes;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingcardService {

    private final ShoppingcardRepository shoppingcardRepository;
    private final ProductClient productClient;

    public ShoppingcardService(ShoppingcardRepository shoppingcardRepository, ProductClient productClient) {
        this.shoppingcardRepository = shoppingcardRepository;
        this.productClient = productClient;
    }

    public Optional<List<ShoppingcardRes>> getShoppingcards(Long userId) throws Exception {
        List<ShoppingcardDO> records = shoppingcardRepository.findByUserId(userId);
        if (records == null || records.size() == 0)
            return Optional.empty();
        List<ShoppingcardRes> resList = ConvertUtils.convert(records, source -> {
            ShoppingcardRes res = new ShoppingcardRes();
            BeanUtils.copyProperties(source, res);
            // 获取产品信息
            ResponseEntity<RestfulResponse<ProductRes>> responseEntity = productClient.getProduct(res.getProductId());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                RestfulResponse<ProductRes> body = responseEntity.getBody();
                res.setProduct(body.getData());
            }
            return res;
        });
        return Optional.of(resList);
    }

    public Optional<Long> addShoppingcard(ShoppingcardReq shoppingcardReq) throws Exception {
        ShoppingcardDO cardProduct = shoppingcardRepository.findByUserIdAndProductId(shoppingcardReq.getUserId(), shoppingcardReq.getProductId());
        if (cardProduct != null && cardProduct.getId() > 0) {
            shoppingcardReq.setId(cardProduct.getId());
            shoppingcardReq.setAmount(shoppingcardReq.getAmount() + cardProduct.getAmount());
            return modifyShoppingcardById(shoppingcardReq);
        }
        ShoppingcardDO shoppingcardDO = new ShoppingcardDO();
        BeanUtils.copyProperties(shoppingcardReq, shoppingcardDO);
        ShoppingcardDO save = shoppingcardRepository.save(shoppingcardDO);
        return Optional.of(save.getId());
    }

    public Optional<Long> modifyShoppingcardById(ShoppingcardReq shoppingcardReq) throws Exception {
        ShoppingcardDO shoppingcardDO = new ShoppingcardDO();
        BeanUtils.copyProperties(shoppingcardReq, shoppingcardDO);
        ShoppingcardDO save = shoppingcardRepository.save(shoppingcardDO);
        return Optional.of(save.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeProduct(ShoppingcardReq shoppingcardReq) throws Exception {
        shoppingcardRepository.deleteByUserIdAndProductId(shoppingcardReq.getUserId(), shoppingcardReq.getProductId());
    }
}
