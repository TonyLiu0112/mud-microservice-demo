package com.tony.demo.microservice.mud.trade.integration;

import com.alibaba.fastjson.JSONObject;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Optional;

/**
 * 账户查询服务
 * <p>
 * Created by Tony on 11/06/2017.
 */
@Service
public class AccountClient {

    private final RestTemplate restTemplate;

    /**
     * XX账户查询服务
     */
    String HBXY_ACCOUNT_QUERY = "http://mud-microservice-thirdparty/hbxy/account/getAccount";

    @Autowired
    public AccountClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 获取交易账号信息
     *
     * @param idNumber
     * @return
     * @throws URISyntaxException
     */
    public Optional<TradeAccountDTO> getTradeAccount(String idNumber) throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(idNumber);
        ResponseEntity<String> response = restTemplate.exchange(HBXY_ACCOUNT_QUERY, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String bodyText = response.getBody();
            return Optional.of(JSONObject.parseObject(bodyText, TradeAccountDTO.class));
        }
        return Optional.empty();
    }

}
