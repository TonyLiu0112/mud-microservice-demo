package com.tony.demo.microservice.mud.services.user.eventuate.command;

import com.tony.demo.microservice.mud.common.eventuate.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCreditCommand implements UserCommand {

    private Money orderTotal;

    private String orderId;

}
