package com.tony.demo.microservice.mud.services.inventory.service.bean;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.services.inventory.dao.entity.InventoryDO;
import com.tony.demo.microservice.mud.services.inventory.dao.repository.InventoryRepository;
import com.tony.demo.microservice.mud.services.inventory.service.bean.response.InventoryRes;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<InventoryRes> getInventory(Long productId) throws Exception {
        InventoryDO record = inventoryRepository.findByProductId(productId);
        return Optional.of(ConvertUtils.convert(record, InventoryRes.class));
    }

}
