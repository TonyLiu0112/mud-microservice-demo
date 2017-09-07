package com.tony.demo.microservice.mud.services.shoppingcard.dao.repository;

import com.tony.demo.microservice.mud.services.shoppingcard.dao.entity.ShoppingcardDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingcardRepository extends JpaRepository<ShoppingcardDO, Long> {
    List<ShoppingcardDO> findByUserId(Long userId);
}
