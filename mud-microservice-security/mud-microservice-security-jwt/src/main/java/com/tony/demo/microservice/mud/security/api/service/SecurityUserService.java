package com.tony.demo.microservice.mud.security.api.service;

import com.tony.demo.microservice.mud.security.api.dao.entity.SecurityUserDO;
import com.tony.demo.microservice.mud.security.api.dao.repository.SecurityUserRepository;
import com.tony.demo.microservice.mud.security.api.service.bean.UserWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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
        return username -> findUserDetails(username).getUser();
    }

    UserWrapper findUserDetails(String loginName) {
        SecurityUserDO account = securityUserRepository.findByLoginName(loginName);
        if (account != null) {
            UserWrapper response = new UserWrapper();
            BeanUtils.copyProperties(account, response);
            User roleUser = new User(account.getLoginName(), account.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("ROLE_USER"));
            response.setUser(roleUser);
            return response;
        } else {
            throw new UsernameNotFoundException("Could not find the login user '" + loginName + "'");
        }
    }

}
