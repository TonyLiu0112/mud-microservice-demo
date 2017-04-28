package com.tony.demo.microservice.mud.user.service;

import com.tony.demo.microservice.mud.user.dao.entity.SecurityUserDO;
import com.tony.demo.microservice.mud.user.dao.repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Login service
 * <p>
 * Created by Tony on 27/02/2017.
 */
@Service
public class SecurityUserService {

    private final SecurityUserRepository securityUserRepository;

    @Autowired
    public SecurityUserService(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    public SecurityUserDO findUserDetails(String loginName) {
        SecurityUserDO userDO = securityUserRepository.findByLoginName(loginName);
        userDO.setPassword("******");
        return userDO;
    }

}
