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

    public int incrementCount() {
        return this.count += 1;
    }

    public int decrementCount() {
        return this.count -= 1;
    }
}
