package com.tony.demo.microservice.mud.trade.controller;

import com.tony.demo.microservice.mud.trade.service.account.FundAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tony on 27/06/2017.
 */
@RestController
@RequestMapping("trade/account")
public class FundTradeAccountController {

    private final FundAccountService fundAccountService;

    @Autowired
    public FundTradeAccountController(FundAccountService fundAccountService) {
        this.fundAccountService = fundAccountService;
    }

    @GetMapping("getAccount")
    public Object getTradeAccount(@RequestParam("idNumber") String idNumber) throws Exception {
        return fundAccountService.getTradeAccount(idNumber);
    }

}
