package com.example.client;

import com.example.domain.InventoryOrderEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;

@KafkaClient
public interface InventoryClient {
    @Topic("inventory")
    void sendInventoryOrderEvent(@Body InventoryOrderEvent event);
}
