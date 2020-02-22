package com.example.listener;

import com.example.client.CustomerClient;
import com.example.integration.Order;
import com.example.integration.OrderEvent;
import com.example.repository.CustomerRepository;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class OrderListener {

    private Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private CustomerClient customerClient;

    @Topic("order")
    public void receive(@KafkaKey Long orderId, OrderEvent event) {
        switch (event.getType()) {
            case ORDER_CREATED -> {
                Order order = event.getOrder();
                logger.info("RECEIVED EVENT: " + event.getType() + " | " + event.getOrder());
                var customerOpt = customerRepository.findById(order.getCustomerId());
                customerOpt.ifPresentOrElse(customer -> {
                    double funds = customer.getCredit();
                    double price = order.getProduct().getPrice();
                    if (price <= funds) {
                        customer.setCredit(funds - price);
                        customerRepository.update(customer);
                        logger.info("CREDIT RESERVED: " + customer);
                    } else {
                        logger.info("NOT ENOUGH CREDITS: " + customer);
                    }
                }, () -> logger.info("CUSTOMER " + order.getCustomerId() + " NOT FOUND"));
            }
            case ORDER_REJECTED -> {
                logger.info("RECEIVED EVENT: " + event.getType() + " | PAYLOAD: " + event.getOrder());
            }
        }
    }

}
