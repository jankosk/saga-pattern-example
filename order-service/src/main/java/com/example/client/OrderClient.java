package com.example.client;

import com.example.domain.OrderEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface OrderClient {
    @Topic("order")
    void send(@KafkaKey Long orderId, OrderEvent orderEvent);
}
