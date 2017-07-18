package com.tony.demo.microservice.mud.trade.service.account;

import com.tony.demo.microservice.mud.trade.integration.AccountClient;
import com.tony.demo.microservice.mud.trade.integration.dto.TradeAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 基金账号交易服务
 * <p>
 * Created by Tony on 27/06/2017.
 */
@Service
public class FundAccountService {

    private final AccountClient accountClient;

    @Autowired
    public FundAccountService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    public TradeAccountDTO getTradeAccount(String idNumber) throws Exception {
        Optional<TradeAccountDTO> optional = accountClient.getTradeAccount(idNumber);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
