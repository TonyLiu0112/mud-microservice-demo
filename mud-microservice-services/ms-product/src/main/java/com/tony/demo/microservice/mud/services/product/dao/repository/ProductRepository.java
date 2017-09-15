package com.tony.demo.microservice.mud.services.product.dao.repository;

import com.tony.demo.microservice.mud.services.product.dao.entity.ProductDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductDO, Long> {
    List<ProductDO> findByIdIn(List<Long> longs);

    Page<ProductDO> findByNameContaining(String name, Pageable pageable);
}
