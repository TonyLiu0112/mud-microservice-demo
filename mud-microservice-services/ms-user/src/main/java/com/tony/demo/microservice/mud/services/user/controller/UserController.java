package com.tony.demo.microservice.mud.services.user.controller;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.services.user.eventuate.command.CreateUserCommand;
import com.tony.demo.microservice.mud.services.user.eventuate.command.UserCommand;
import com.tony.demo.microservice.mud.services.user.eventuate.entity.User;
import com.tony.demo.microservice.mud.services.user.service.bean.UserRegisterReq;
import io.eventuate.sync.AggregateRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private final AggregateRepository<User, UserCommand> userRepository;

    public UserController(AggregateRepository<User, UserCommand> userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("register")
    public void register(@RequestBody UserRegisterReq userRegisterReq) {
        userRepository.save(new CreateUserCommand(userRegisterReq.getName(), new Money(10000)));
    }

}
