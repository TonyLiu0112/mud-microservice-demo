package com.tony.demo.microservice.mud.dao.repository;

import com.tony.demo.microservice.mud.dao.entity.SecurityUserDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for SecurityUser.
 * <p>
 * Created by Tony on 27/02/2017.
 */
public interface SecurityUserRepository extends JpaRepository<SecurityUserDO, Long> {

    SecurityUserDO findByLoginName(String loginName);
}
