package com.example.listener;

import com.example.EventType;
import com.example.client.OrderClient;
import com.example.domain.OrderEvent;
import com.example.domain.OrderStatus;
import com.example.integration.CustomerOrderEvent;
import com.example.repository.OrderRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@KafkaListener
public class CustomerListener {

    private Logger logger = LoggerFactory.getLogger(CustomerListener.class);

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderClient orderClient;

    @Topic("customer")
    void receive(@Body CustomerOrderEvent event) {
        logger.info("RECEIVED EVENT: " + event);
        switch (event.getType()) {
            case ORDER_PAID -> {
                var orderId = event.getOrder().getId();
                var orderOpt = orderRepository.findById(orderId);
                orderOpt.ifPresentOrElse(order -> {
                    order.setStatus(OrderStatus.CONFIRMED);
                    orderRepository.update(order);
                    var orderEvent = new OrderEvent(EventType.ORDER_CONFIRMED, order);
                    orderClient.send(orderEvent);
                    logger.info("ORDER CONFIRMED " + order);
                }, () -> logger.error("ORDER " + orderId + " NOT FOUND"));
            }
            case ORDER_PAYMENT_FAILURE -> {}
        }
    }
}
