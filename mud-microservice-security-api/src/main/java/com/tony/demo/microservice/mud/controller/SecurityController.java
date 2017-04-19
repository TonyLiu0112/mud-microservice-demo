package com.tony.demo.microservice.mud.controller;

import com.tony.demo.microservice.mud.service.AuthenticationService;
import com.tony.demo.microservice.mud.service.dto.SecurityUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 安全性相关控制器
 * <p>
 * Created by Tony on 27/02/2017.
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    private final AuthenticationService authenticationService;

    private final TokenStore tokenStore;

    @Autowired
    public SecurityController(AuthenticationService authenticationService, TokenStore tokenStore) {
        this.authenticationService = authenticationService;
        this.tokenStore = tokenStore;
    }

    @GetMapping("login")
    public String login() {
        return "不支持浏览器登入";
    }

    @PostMapping("doLogin")
    public Map<String, Object> doLogin(@RequestBody SecurityUserReq userReq, HttpServletRequest httpReq) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        if (authenticationService.doAuthentication(userReq)) {
            response.put("success", true);
            response.put("jsessionid", httpReq.getSession().getId());
        }
        return response;
    }

    @PostMapping("doLogout")
    public Map<String, Object> doLogout(HttpServletRequest httpReq, HttpServletResponse httpRes) {
        Map<String, Object> response = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            new SecurityContextLogoutHandler().logout(httpReq, httpRes, auth);
        response.put("success", true);
        return response;
    }


    @GetMapping("/session_timeout")
    public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getHeader("x-requested-with") != null) {
            response.getWriter().print("{\"session_invalid\": true}");
            response.getWriter().close();
        } else {
            response.sendRedirect("login");
        }
    }

    @GetMapping("/tokens")
    public List<String> getTokens() {
        return tokenStore.findTokensByClientId("123").stream().map(OAuth2AccessToken::getValue).collect(Collectors.toList());
    }

}
