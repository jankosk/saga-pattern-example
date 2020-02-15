package com.example.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import com.example.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
