package com.example.listener;

import com.example.integration.Order;
import com.example.integration.OrderEvent;
import com.example.repository.InventoryRepository;
import com.example.repository.ItemRepository;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.ConcurrentHashMap;

@KafkaListener
public class EventListener {

    private Logger logger = LoggerFactory.getLogger(EventListener.class);

    @Inject
    private InventoryRepository inventoryRepository;
    @Inject
    private ItemRepository itemRepository;

    private ConcurrentHashMap<String, Order> pendingOrders = new ConcurrentHashMap<>();

    @Topic("order")
    void receive(@KafkaKey Long orderId, @Body OrderEvent orderEvent) {
        logger.info("RECEIVED EVENT: " + orderEvent);
        switch (orderEvent.getType()) {
            case ORDER_CREATED -> {
                var order = orderEvent.getOrder();
                var itemId = order.getProduct().getItemId();
                inventoryRepository
                        .findByItemItemId(itemId)
                        .ifPresentOrElse(inventory -> {
                            if (inventory.getCount() >= 1) {
                                inventory.decrementCount();
                                inventoryRepository.update(inventory);
                                logger.info("ORDER RESERVED");
                            } else {
                                logger.info("ITEM OUT OF STOCK " + itemId);
                            }
                        }, () -> logger.info("ITEM " + itemId + " NOT FOUND"));
            }
        }
    }
}
