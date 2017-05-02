package com.tony.demo.microservice.mud.web.manager.service.downstream;

import com.tony.demo.microservice.mud.web.manager.dto.ActivityDto;

import java.util.List;
import java.util.Optional;

public interface ActivityService {

    String URI = "http://mud-microservice-activity/%s";

    default Optional<List<ActivityDto>> findAllActivity() throws Exception {
        return Optional.empty();
    }

    default Optional<ActivityDto> findByName(String name) throws Exception {
    	return Optional.empty();
    }

}
