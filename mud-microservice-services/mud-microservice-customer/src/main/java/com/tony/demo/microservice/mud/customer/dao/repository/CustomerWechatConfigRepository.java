package com.tony.demo.microservice.mud.customer.dao.repository;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerWechatConfigDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for CustomerWechatConfigEntity
 * <p>
 * Created by Tony on 09/02/2017.
 */
public interface CustomerWechatConfigRepository extends JpaRepository<CustomerWechatConfigDO, Long> {
}
