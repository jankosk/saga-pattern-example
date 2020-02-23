package com.example.service;

import com.example.EventType;
import com.example.client.OrderClient;
import com.example.domain.Order;
import com.example.domain.OrderEvent;
import com.example.domain.OrderStatus;
import com.example.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderClient orderClient;

    public void confirmOrder(Order ord) {
        var orderId = ord.getId();
        var orderOpt = orderRepository.findById(orderId);
        orderOpt.ifPresentOrElse(order -> {
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.update(order);
            var orderEvent = new OrderEvent(EventType.ORDER_CONFIRMED, order);
            orderClient.send(orderEvent);
            logger.info("ORDER CONFIRMED " + order);
        }, () -> logger.error("ORDER " + orderId + " NOT FOUND"));
    }

    public void cancelOrder(Order ord) {
        var orderId = ord.getId();
        var orderOpt = orderRepository.findById(orderId);
        orderOpt.ifPresentOrElse(order -> {
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.REJECTED);
                orderRepository.update(order);
                var orderEvent = new OrderEvent(EventType.ORDER_REJECTED, order);
                orderClient.send(orderEvent);
                logger.info("ORDER REJECTED " + order);
            }
        }, () -> logger.error("ORDER " + orderId + " NOT FOUND"));
    }
}
