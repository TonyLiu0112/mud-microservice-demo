package com.tony.demo.microservice.mud.services.es.endpoints;

import com.wrench.utils.restfulapi.response.RestfulBuilder;
import com.wrench.utils.restfulapi.response.RestfulResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
public class EventEndpoints {

    public ResponseEntity<RestfulResponse> addEvent() {
        return RestfulBuilder.ok();
    }



}
