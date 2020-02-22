package com.example.controller;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.Optional;

@Controller("/customer")
public class CustomerController {

    @Inject
    private CustomerRepository customerRepository;

    @Get
    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Post
    public Customer createCustomer(@Body Customer customer) {
        return customerRepository.save(customer);
    }
}
