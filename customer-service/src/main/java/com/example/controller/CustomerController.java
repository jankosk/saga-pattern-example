package com.example.controller;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller("/customer")
public class CustomerController {

    @Inject
    private CustomerRepository customerRepository;

    @Get("/{customerId}")
    public Optional<Customer> getCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Get
    public List<Customer> getAllCustomers() {
        var customerIter = customerRepository.findAll();
        return StreamSupport.stream(customerIter.spliterator(), false)
            .collect(Collectors.toList());
    }

    @Post
    public Customer createCustomer(@Body Customer customer) {
        return customerRepository.save(customer);
    }
}
