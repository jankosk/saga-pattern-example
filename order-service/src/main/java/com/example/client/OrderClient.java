package com.example.client;

import com.example.domain.OrderEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;

@KafkaClient
public interface OrderClient {
    @Topic("order")
    void send(@Body OrderEvent orderEvent);
}
