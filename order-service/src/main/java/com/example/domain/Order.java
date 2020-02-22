package com.example.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_table")
@Data
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String customerId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    @NotNull
    private OrderStatus status;
}
