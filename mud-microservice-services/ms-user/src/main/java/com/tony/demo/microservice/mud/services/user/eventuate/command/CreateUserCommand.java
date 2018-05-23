package com.tony.demo.microservice.mud.services.user.eventuate.command;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserCommand implements UserCommand {

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户信用等级
     */
    private Money creditLimit;

}
