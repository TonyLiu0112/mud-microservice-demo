package com.tony.demo.microservice.mud.web.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Tony on 27/06/2017.
 */
@RestController
@RequestMapping("trade")
public class TradeController {

    private final RestTemplate restTemplate;

    @Autowired
    public TradeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("getAccount")
    public Object getTradeAccount(@RequestParam("idNumber") String idNumber) throws Exception {
        return restTemplate.getForObject("http://mud-microservice-trade/trade/account/getAccount?idNumber=" + idNumber, String.class);
    }

}
