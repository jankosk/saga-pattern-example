package com.example.repository;

import com.example.domain.Item;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
}
