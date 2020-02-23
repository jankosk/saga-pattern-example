package com.example.client;

import com.example.Event;
import com.example.domain.Customer;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.annotation.Body;

@KafkaClient
public interface CustomerClient {
    @Topic("order")
    void send(@Body Event<Customer> customerEvent);
}
