package com.example.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Item {
    @Id
    private String itemId;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private double price;

    private double weight;

    private String dimensions;
}
