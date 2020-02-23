package com.example.controller;

import com.example.domain.Inventory;
import com.example.repository.InventoryRepository;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller("/inventory")
public class InventoryController {

    @Inject
    private InventoryRepository inventoryRepository;

    @Post
    public Inventory createItemInventory(@Body Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Post("/increment-count/{itemId}{?count}")
    public Optional<Inventory> incrementItemCount(@PathVariable String itemId, @QueryValue Optional<Integer> count) {
        var inventoryOpt = inventoryRepository.findByItemItemId(itemId);
        var num = count.orElse(1);
        inventoryOpt.ifPresent(inventory -> {
            inventory.incrementCountBy(num);
            inventoryRepository.update(inventory);
        });
        return inventoryOpt;
    }

    @Get
    public List<Inventory> getAllItemInventory() {
        var inventoryIterable = inventoryRepository.findAll();
        return StreamSupport.stream(inventoryIterable.spliterator(), false)
            .collect(Collectors.toList());
    }
}
