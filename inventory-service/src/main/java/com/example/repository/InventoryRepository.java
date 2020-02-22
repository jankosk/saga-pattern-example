package com.example.repository;

import com.example.domain.Inventory;
import com.example.domain.Item;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    Optional<Inventory> findByItem(Item item);
}
