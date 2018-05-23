package com.tony.demo.microservice.mud.common.eventuate.event.user;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import com.tony.demo.microservice.mud.common.eventuate.event.UserEvent;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class UserCreatedEvent implements UserEvent {
  private String name;
  private Money creditLimit;

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  private UserCreatedEvent() {
  }

  public UserCreatedEvent(String name, Money creditLimit) {
    this.name = name;
    this.creditLimit = creditLimit;
  }

  public String getName() {
    return name;
  }

  public Money getCreditLimit() {
    return creditLimit;
  }
}
