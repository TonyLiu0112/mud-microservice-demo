package com.tony.demo.microservice.mud.web.manager.controller;

import com.tony.demo.microservice.mud.common.AbstractController;
import com.tony.demo.microservice.mud.web.manager.dto.IndexDto;
import com.tony.demo.microservice.mud.web.manager.dto.UserDto;
import com.tony.demo.microservice.mud.web.manager.service.SimpleService;
import com.tony.demo.microservice.mud.web.manager.service.downstream.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("index")
public class IndexController extends AbstractController {

    private final SimpleService simpleService;
    private final RemoteUserService remoteUserService;

    @Autowired
    public IndexController(SimpleService simpleService, RemoteUserService remoteUserService) {
        this.simpleService = simpleService;
        this.remoteUserService = remoteUserService;
    }

    @GetMapping("index")
    public String index(Model model) throws Exception {
        List<IndexDto> dtoList = simpleService.findCustomerAndActivity();
        model.addAttribute("dataList", dtoList);
        return "index";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("getUser")
    public
    @ResponseBody
    Map<String, Object> getUserInfo(HttpSession httpSession) {
        httpSession.setAttribute("manager", "for test");
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        String loginName = securityContext.getAuthentication().getPrincipal().toString();
        Map<String, Object> res = remoteUserService.getByLoginName(loginName);
        if (res == null || res.isEmpty())
            return fail();
        Map<String, Object> userInfo = (Map<String, Object>) res.get("results");
        UserDto userDto = new UserDto();
        userDto.setLoginName(loginName);
        userDto.setPassword(userInfo.get("password").toString());
        userDto.setEmail(userInfo.get("email").toString());
        userDto.setNickname(userInfo.get("nickname").toString());
        userDto.setPhone(userInfo.get("phone").toString());
        userDto.setSex((Integer) userInfo.get("sex"));
        httpSession.setAttribute("user", userDto);
        return data(userDto);
    }

    /**
     * 测试获得sso的权限信息
     *
     * @param httpSession
     * @return
     */
    @SuppressWarnings("serial")
    @GetMapping("userDetails")
    public
    @ResponseBody
    Map<String, Object> getUserDetails(HttpSession httpSession) {
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        final String roles = securityContext.getAuthentication().getAuthorities().stream()
                .map(Object::toString).collect(Collectors.joining(","));
        return data(new HashMap<String, Object>() {{
            put("userName", securityContext.getAuthentication().getPrincipal().toString());
            put("roles", roles);
        }});
    }

    @GetMapping("{name}")
    public
    @ResponseBody
    Map<String, Object> findByName(@PathVariable("name") String name) {
        try {
            return data(simpleService.findActivityByName(name));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return fail();
        }
    }

}
