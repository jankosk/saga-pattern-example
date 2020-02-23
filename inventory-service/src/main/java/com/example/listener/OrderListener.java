package com.example.listener;

import com.example.EventType;
import com.example.client.InventoryClient;
import com.example.domain.InventoryOrderEvent;
import com.example.integration.Order;
import com.example.integration.OrderEvent;
import com.example.repository.InventoryRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@KafkaListener
public class OrderListener {

    private Logger logger = LoggerFactory.getLogger(OrderListener.class);
    private ConcurrentHashMap<Long, Order> pendingOrders = new ConcurrentHashMap<>();

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
                                pendingOrders.put(order.getId(), order);
                                logger.info("ITEM " + itemId + " RESERVED");
                            } else {
                                var event = new InventoryOrderEvent(EventType.ORDER_ITEM_OUT_OF_STOCK, inventory, order);
                                inventoryClient.sendInventoryOrderEvent(event);
                                logger.info("ITEM OUT OF STOCK " + itemId);
                            }
                        }, () -> logger.error("ITEM " + itemId + " NOT FOUND"));
            }
            case ORDER_REJECTED -> {
                var id = orderEvent.getOrder().getId();
                var ord = Optional.ofNullable(pendingOrders.get(id));
                ord.ifPresent(order ->
                        inventoryRepository
                                .findByItemItemId(order.getItemId())
                                .ifPresent(inventory -> {
                                    inventory.incrementCountBy(1);
                                    inventoryRepository.update(inventory);
                                    pendingOrders.remove(id);
                                    logger.info("ITEM " + order.getItemId() + " COUNT COMPENSATED");
                                }));
            }
            case ORDER_CONFIRMED -> pendingOrders.remove(orderEvent.getOrder().getId());
        }
    }
}
