package com.tony.demo.microservice.mud.trade.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

/**
 * 账户查询服务
 * <p>
 * Created by Tony on 11/06/2017.
 */
@Service
public class AccountClient {

    private final RestTemplate restTemplate;

    /**
     * 华宝账户查询服务
     */
    String HBXY_ACCOUNT_QUERY = "http://mud-microservice-thirdparty/hbxy/account/getAccount";

    @Autowired
    public AccountClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getAccount() throws URISyntaxException {
        String responseBody = restTemplate.getForObject(HBXY_ACCOUNT_QUERY, String.class);
        return null;
    }

}
