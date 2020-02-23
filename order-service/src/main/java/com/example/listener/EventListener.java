package com.example.listener;

import com.example.integration.CustomerOrderEvent;
import com.example.integration.InventoryOrderEvent;
import com.example.service.OrderService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@KafkaListener
public class EventListener {

    private Logger logger = LoggerFactory.getLogger(EventListener.class);

    @Inject
    private OrderService orderService;

    @Topic("customer")
    void receiveCustomer(@Body CustomerOrderEvent event) {
        logger.info("RECEIVED EVENT: " + event);
        switch (event.getType()) {
            case ORDER_PAID -> orderService.confirmOrder(event.getOrder());
            case ORDER_PAYMENT_FAILURE -> orderService.cancelOrder(event.getOrder());
        }
    }

    @Topic("inventory")
    void receiveInventory(@Body InventoryOrderEvent event) {
        logger.info("RECEIVED EVENT: " + event);
        switch (event.getType()) {
            case ORDER_ITEM_OUT_OF_STOCK -> orderService.cancelOrder(event.getOrder());
        }
    }
}
