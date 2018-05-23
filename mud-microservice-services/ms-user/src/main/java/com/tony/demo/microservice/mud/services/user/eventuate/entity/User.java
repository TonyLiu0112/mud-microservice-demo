package com.tony.demo.microservice.mud.services.user.eventuate.entity;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.common.eventuate.event.user.UserCreatedEvent;
import com.tony.demo.microservice.mud.common.eventuate.event.user.UserCreditLimitExceededEvent;
import com.tony.demo.microservice.mud.common.eventuate.event.user.UserCreditReservedEvent;
import com.tony.demo.microservice.mud.services.user.eventuate.ReservedCreditTracker;
import com.tony.demo.microservice.mud.services.user.eventuate.command.CreateUserCommand;
import com.tony.demo.microservice.mud.services.user.eventuate.command.ReserveCreditCommand;
import com.tony.demo.microservice.mud.services.user.eventuate.command.UserCommand;
import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;

import java.util.List;

/**
 * 用户聚合对象
 *
 * @author Tony
 */
public class User extends ReflectiveMutableCommandProcessingAggregate<User, UserCommand> {

    private Money creditLimit;
    private String name;
    private ReservedCreditTracker reservedCreditTracker;

    // command process

    public List<Event> process(CreateUserCommand createUserCommand) {
        return EventUtil.events(new UserCreatedEvent(createUserCommand.getName(), createUserCommand.getCreditLimit()));
    }

    public List<Event> process(ReserveCreditCommand reserveCreditCommand) {
        if (available().isGreaterThanOrEqual(reserveCreditCommand.getOrderTotal())) {
            return EventUtil.events(new UserCreditReservedEvent(reserveCreditCommand.getOrderId(), reserveCreditCommand.getOrderTotal()));
        } else {
            return EventUtil.events(new UserCreditLimitExceededEvent());
        }
    }

    private Money available() {
        return creditLimit.subtract(reservedCreditTracker.reservedCredit());
    }

    // event process

    public void apply(UserCreatedEvent customerCreatedEvent) {
        this.name = customerCreatedEvent.getName();
        this.creditLimit = customerCreatedEvent.getCreditLimit();
        this.reservedCreditTracker = new ReservedCreditTracker();
    }

    public void apply(UserCreditReservedEvent customerCreditReservedEvent) {

    }

    public void apply(UserCreditLimitExceededEvent customerCreditLimitExceededEvent) {
        // Do nothing
    }
}
