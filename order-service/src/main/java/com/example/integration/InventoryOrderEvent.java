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
public class InventoryOrderEvent {
    private EventType type;
    private Object inventory;
    private Order order;
}
