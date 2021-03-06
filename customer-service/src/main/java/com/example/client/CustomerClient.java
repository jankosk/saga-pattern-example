package com.example.client;

import com.example.domain.CustomerOrderEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;

@KafkaClient
public interface CustomerClient {
    @Topic("customer")
    void sendCustomerOrderEvent(@Body CustomerOrderEvent event);
}
