package com.example.integration;

import com.example.EventType;
import com.example.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerOrderEvent {
    private EventType type;
    private Customer customer;
    private Order order;
}
