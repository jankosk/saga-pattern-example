package com.example.integration;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private Long id;
    private String customerId;
    private String itemId;
    private double totalPrice;
    private OrderStatus status;
}
