package com.example.repository;

import com.example.domain.Customer;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
}
