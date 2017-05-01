package com.tony.demo.microservice.mud.web.store.controller;

import com.tony.demo.microservice.mud.web.store.dto.IndexDto;
import com.tony.demo.microservice.mud.web.store.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 07/04/2017.
 */
@Controller
@RequestMapping("index")
public class IndexController {

    private final SimpleService simpleService;
    private final OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public IndexController(SimpleService simpleService, OAuth2RestTemplate oAuth2RestTemplate) {
        this.simpleService = simpleService;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @GetMapping("index")
    public String index(Model model) {
        List<IndexDto> dtoList = simpleService.findCustomerAndActivity();
        model.addAttribute("dataList", dtoList);
        return "index";
    }

    @GetMapping("showSession")
    public @ResponseBody
    Object show(HttpSession httpSession) {
        String result = oAuth2RestTemplate.getForObject("http://mud-microservice-user/user/printSession", String.class);
        Map<String, Object> rst = new HashMap<>();
        // session在manager项目设置
        rst.put("current session", httpSession.getAttribute("manager"));
        // session来自user服务，为 null
        rst.put("user    session", result);
        return rst;
    }

}
