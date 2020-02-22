package com.example.integration;

import lombok.Data;

@Data
public class Product {
    private String itemId;
    private String name;
    private String description;
    private double price;
}