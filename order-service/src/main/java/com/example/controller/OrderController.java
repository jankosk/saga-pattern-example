package com.example.controller;

import com.example.client.OrderClient;
import com.example.repository.OrderRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import com.example.domain.Order;
import com.example.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public Order createOrder(@Body Order order) {
        order.setStatus(OrderStatus.PENDING);
        var saved_order = orderRepository.save(order);
        logger.info("ORDER " + order.getId() + " CREATED");
        orderClient.createOrder(saved_order.getId(), saved_order);
        return saved_order;
    }

}
