package com.tony.demo.microservice.mud.services.shoppingcard.service;

import com.tony.demo.microservice.mud.services.shoppingcard.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.shoppingcard.dao.entity.ShoppingcardDO;
import com.tony.demo.microservice.mud.services.shoppingcard.dao.repository.ShoppingcardRepository;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.request.ShoppingcardReq;
import com.tony.demo.microservice.mud.services.shoppingcard.service.bean.response.ShoppingcardRes;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingcardService {

    private final ShoppingcardRepository shoppingcardRepository;

    public ShoppingcardService(ShoppingcardRepository shoppingcardRepository) {
        this.shoppingcardRepository = shoppingcardRepository;
    }

    public Optional<List<ShoppingcardRes>> getShoppingcards(Long userId) throws Exception {
        List<ShoppingcardDO> records = shoppingcardRepository.findByUserId(userId);
        if (records == null || records.size() == 0)
            return Optional.empty();
        List<ShoppingcardRes> resList = ConvertUtils.convert(records, source -> {
            ShoppingcardRes res = new ShoppingcardRes();
            BeanUtils.copyProperties(source, res);
            // TODO: 20/08/2017 获取产品信息
            return res;
        });
        return Optional.of(resList);
    }

    public Optional<Long> addShoppingcard(ShoppingcardReq shoppingcardReq) throws Exception {
        ShoppingcardDO shoppingcardDO = new ShoppingcardDO();
        BeanUtils.copyProperties(shoppingcardReq, shoppingcardDO);
        ShoppingcardDO save = shoppingcardRepository.save(shoppingcardDO);
        return Optional.of(save.getId());
    }
}
