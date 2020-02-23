package com.example.controller;

import com.example.EventType;
import com.example.client.OrderClient;
import com.example.domain.OrderEvent;
import com.example.repository.OrderRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import com.example.domain.Order;
import com.example.domain.OrderStatus;
import io.micronaut.validation.Validated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Validated
@Controller("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private OrderClient orderClient;

    @Get("/{id}")
    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Get
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(orderList::add);
        return orderList;
    }

    @Post
    public Order createOrder(@Body Order order) {
        order.setStatus(OrderStatus.PENDING);
        var saved_order = orderRepository.save(order);
        orderClient.send(new OrderEvent(EventType.ORDER_CREATED, saved_order));
        logger.info("ORDER CREATED: " + saved_order);
        return saved_order;
    }

}
