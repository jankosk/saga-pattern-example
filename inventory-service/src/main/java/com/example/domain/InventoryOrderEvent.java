package com.example.domain;

import com.example.EventType;
import com.example.integration.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryOrderEvent {
    private EventType type;
    private Inventory inventory;
    private Order order;
}
