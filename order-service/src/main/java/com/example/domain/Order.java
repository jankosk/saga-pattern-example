package com.example.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_table")
@Data
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String customerId;
    private String itemId;
    private OrderStatus status;
}
