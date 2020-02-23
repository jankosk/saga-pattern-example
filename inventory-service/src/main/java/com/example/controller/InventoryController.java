package com.example.controller;

import com.example.domain.Inventory;
import com.example.repository.InventoryRepository;
import com.example.repository.ItemRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller("/inventory")
public class InventoryController {

    @Inject
    private InventoryRepository inventoryRepository;

    @Inject
    private ItemRepository itemRepository;

    @Post
    public Inventory createItemInventory(@Body Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Get
    public List<Inventory> getAllItemInventory() {
        var inventoryIterable = inventoryRepository.findAll();
        return StreamSupport.stream(inventoryIterable.spliterator(), false)
            .collect(Collectors.toList());
    }
}
