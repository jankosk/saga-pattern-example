package com.example.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "itemId")
    private Item item;

    @NotNull
    private int count;

    @NotNull
    private String location;

    public int incrementCountBy(int num) {
        return this.count += num;
    }

    public int decrementCountBy(int num) {
        return this.count -= num;
    }
}
