package com.example.integration;

import com.example.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOrderEvent {
    private EventType type;
    private Object inventory;
    private Order order;
}
