package com.tony.demo.microservice.mud.services.inventory.dao.repository;

import com.tony.demo.microservice.mud.services.inventory.dao.entity.InventoryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryDO, Long> {
    InventoryDO findByProductId(Long productId);
}
