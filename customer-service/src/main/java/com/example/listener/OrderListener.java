package com.example.listener;


import com.example.integration.Order;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class OrderListener {

    private Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @Topic("order")
    public void receive(@KafkaKey Long orderId, Order order) {
        logger.info("RECEIVED ORDER " + order);
    }
}
