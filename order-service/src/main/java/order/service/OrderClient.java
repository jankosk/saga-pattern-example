package order.service;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.annotation.Body;
import order.service.domain.Order;

@KafkaClient
public interface OrderClient {
    @Topic("order")
    void createOrder(@Body Order order);

    void cancelOrder(@Body Order order);
}
