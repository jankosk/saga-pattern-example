package com.example.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String customerId;
    private String itemId;
    private OrderStatus status;

    public Order() {}

    public Order(String customerId, String itemId) {
        this.customerId = customerId;
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus state) {
        this.status = state;
    }

    public Long getId() {
        return id;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
