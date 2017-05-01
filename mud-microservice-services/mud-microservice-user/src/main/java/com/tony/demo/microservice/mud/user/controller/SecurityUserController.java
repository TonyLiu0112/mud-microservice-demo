package com.tony.demo.microservice.mud.user.controller;

import com.tony.demo.microservice.mud.common.AbstractController;
import com.tony.demo.microservice.mud.user.service.SecurityUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("user")
public class SecurityUserController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(SecurityUserController.class);

    private final SecurityUserService securityUserService;

    @Autowired
    public SecurityUserController(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @GetMapping("getByLoginName")
    public Map<String, Object> getUserByLoginName(@RequestParam("loginName") String loginName) {
        try {
            return data(securityUserService.findUserDetails(loginName));
        } catch (Exception e) {
            logger.error("", e);
            return fail();
        }
    }

    @GetMapping("printSession")
    public Map<String, Object> printSession(HttpSession httpSession) {
        try {
            return data(httpSession.getAttribute("manager"));
        } catch (Exception e) {
            logger.error("", e);
            return fail();
        }
    }

}
