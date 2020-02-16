package com.example.client;

import com.example.domain.Order;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.annotation.Body;

@KafkaClient
public interface OrderClient {
    @Topic("order")
    void createOrder(@KafkaKey Long id, Order order);

    void cancelOrder(@KafkaKey Long id, Order order);
}
