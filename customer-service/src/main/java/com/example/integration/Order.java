package com.example.integration;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private String customerId;
    private String itemId;
    private String status;
}
