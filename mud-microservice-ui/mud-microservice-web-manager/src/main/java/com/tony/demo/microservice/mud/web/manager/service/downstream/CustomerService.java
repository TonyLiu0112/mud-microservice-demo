package com.tony.demo.microservice.mud.web.manager.service.downstream;

import com.tony.demo.microservice.mud.web.manager.dto.CustomerDto;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    String URI = "http://mud-microservice-customer/%s";

    default Optional<List<CustomerDto>> findByActivityId(long id) throws Exception {
        return Optional.empty();
    }

}
