package com.tony.demo.microservice.mud.user.dao.repository;

import com.tony.demo.microservice.mud.user.dao.entity.SecurityUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUserDO, Long> {
    SecurityUserDO findByLoginName(String loginName);
}
