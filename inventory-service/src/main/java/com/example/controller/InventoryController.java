package com.example.controller;

import com.example.domain.Inventory;
import com.example.repository.InventoryRepository;
import com.example.repository.ItemRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;

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
}
