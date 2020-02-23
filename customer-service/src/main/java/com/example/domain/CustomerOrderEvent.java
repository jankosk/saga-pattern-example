package com.example.domain;

import com.example.EventType;
import com.example.integration.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerOrderEvent {
    private EventType type;
    private Customer customer;
    private Order order;
}
