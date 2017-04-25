package com.tony.demo.microservice.mud.web.manager.controller;

import com.tony.demo.microservice.mud.web.manager.dto.IndexDto;
import com.tony.demo.microservice.mud.web.manager.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController {

    private final SimpleService simpleService;

    @Autowired
    public IndexController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @GetMapping("index")
    public String index(Model model) throws Exception {
        List<IndexDto> dtoList = simpleService.findCustomerAndActivity();
        model.addAttribute("dataList", dtoList);
        return "index";
    }

}
