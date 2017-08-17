package com.tony.demo.microservice.mud.security.sso.service;

import com.tony.demo.microservice.mud.security.sso.dao.entity.SecurityUserDO;
import com.tony.demo.microservice.mud.security.sso.dao.repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public UserDetailsService getUserDetailsService() {
        return this::findUserDetails;
    }

    private UserDetails findUserDetails(String loginName) {
        SecurityUserDO account = securityUserRepository.findByLoginName(loginName);
        if (account != null) {
            return new User(account.getLoginName(), account.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ACTUATOR"));
        } else {
            throw new UsernameNotFoundException("Could not find the login user '" + loginName + "'");
        }
    }

}
