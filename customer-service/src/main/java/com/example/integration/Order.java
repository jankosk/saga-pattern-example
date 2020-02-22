package com.example.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private String customerId;
    private Product product;
    private OrderStatus status;
}
