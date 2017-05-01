package com.tony.demo.microservice.mud.web.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Tony on 07/04/2017.
 */
@Controller
@RequestMapping("index")
public class IndexController {

//    private final SimpleService simpleService;
//
//    @Autowired
//    public IndexController(SimpleService simpleService) {
//        this.simpleService = simpleService;
//    }
//
//    @GetMapping("index")
//    public String index(Model model) {
//        List<IndexDto> dtoList = simpleService.findCustomerAndActivity();
//        model.addAttribute("dataList", dtoList);
//        return "index";
//    }

    @GetMapping("showSession")
    public @ResponseBody
    Object show(HttpSession httpSession) {
        return httpSession.getAttribute("manager");
    }

}
