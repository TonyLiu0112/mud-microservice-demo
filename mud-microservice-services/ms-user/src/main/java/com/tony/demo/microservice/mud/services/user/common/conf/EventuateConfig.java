package com.tony.demo.microservice.mud.services.user.common.conf;

import com.tony.demo.microservice.mud.services.user.eventuate.command.UserCommand;
import com.tony.demo.microservice.mud.services.user.eventuate.entity.User;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableEventHandlers
@Import(EventuateDriverConfiguration.class)
public class EventuateConfig {

    @Bean
    public AggregateRepository<User, UserCommand> userRepository(EventuateAggregateStore eventStore) {
        return new AggregateRepository<>(User.class, eventStore);
    }

}
