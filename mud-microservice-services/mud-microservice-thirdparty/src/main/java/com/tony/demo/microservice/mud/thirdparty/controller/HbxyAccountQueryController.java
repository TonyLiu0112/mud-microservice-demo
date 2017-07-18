package com.tony.demo.microservice.mud.thirdparty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * XXXX账户类查询接口
 * <p>
 * Created by Tony on 11/06/2017.
 */
@RestController
@RequestMapping("hbxy/account")
public class HbxyAccountQueryController {

    @GetMapping("getAccount")
    public Object getTradeAccount(@RequestParam("idNumber") String idNumber) {
        System.out.println(idNumber);
        return new AccountResponse("1019291", "中国工商银行", "1020011**1928, ", "007", "01");
    }

    private class AccountResponse {
        private String tradeAcco;

        private String bankName;

        private String bankAcco;

        private String bankSerial;

        private String capitalMode;

        public AccountResponse(String tradeAcco, String bankName, String bankAcco, String bankSerial, String capitalMode) {
            this.tradeAcco = tradeAcco;
            this.bankName = bankName;
            this.bankAcco = bankAcco;
            this.bankSerial = bankSerial;
            this.capitalMode = capitalMode;
        }

        public String getTradeAcco() {
            return tradeAcco;
        }

        public void setTradeAcco(String tradeAcco) {
            this.tradeAcco = tradeAcco;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankAcco() {
            return bankAcco;
        }

        public void setBankAcco(String bankAcco) {
            this.bankAcco = bankAcco;
        }

        public String getBankSerial() {
            return bankSerial;
        }

        public void setBankSerial(String bankSerial) {
            this.bankSerial = bankSerial;
        }

        public String getCapitalMode() {
            return capitalMode;
        }

        public void setCapitalMode(String capitalMode) {
            this.capitalMode = capitalMode;
        }
    }

}
