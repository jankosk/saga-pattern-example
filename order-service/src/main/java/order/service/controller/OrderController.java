package order.service.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
import order.service.domain.Order;
import order.service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller("/order")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Inject
    OrderRepository orderRepository;

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
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

}
