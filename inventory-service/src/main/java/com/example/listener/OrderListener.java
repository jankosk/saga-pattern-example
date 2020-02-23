package com.example.listener;

import com.example.EventType;
import com.example.client.InventoryClient;
import com.example.domain.InventoryOrderEvent;
import com.example.integration.OrderEvent;
import com.example.repository.InventoryRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@KafkaListener
public class OrderListener {

    private Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @Inject
    private InventoryRepository inventoryRepository;
    @Inject
    private InventoryClient inventoryClient;

    @Topic("order")
    void receive(@Body OrderEvent orderEvent) {
        logger.info("RECEIVED EVENT: " + orderEvent);
        switch (orderEvent.getType()) {
            case ORDER_CREATED -> {
                var order = orderEvent.getOrder();
                var itemId = order.getItemId();
                inventoryRepository
                        .findByItemItemId(itemId)
                        .ifPresentOrElse(inventory -> {
                            if (inventory.getCount() >= 1) {
                                inventory.decrementCountBy(1);
                                inventoryRepository.update(inventory);
                                var event = new InventoryOrderEvent(EventType.ORDER_ITEM_RESERVED, inventory, order);
                                inventoryClient.sendInventoryOrderEvent(event);
                                logger.info("ITEM " + itemId + " RESERVED");
                            } else {
                                logger.info("ITEM OUT OF STOCK " + itemId);
                            }
                        }, () -> logger.info("ITEM " + itemId + " NOT FOUND"));
            }
            case ORDER_REJECTED -> {}
        }
    }
}
