package com.example.client;

import com.example.Event;
import com.example.domain.Order;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface OrderClient {
    @Topic("order")
    void createOrder(@KafkaKey Long id, Event<Order> order);

    void cancelOrder(@KafkaKey Long id, Event<Order> order);
}
