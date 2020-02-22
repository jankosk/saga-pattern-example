package com.example.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String itemId;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private double price;
}
