package com.example.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private Long id;
    private String customerId;
    private String itemId;
    private double totalPrice;
    private OrderStatus status;
}
