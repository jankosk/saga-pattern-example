package com.example.listener;

import com.example.EventType;
import com.example.client.CustomerClient;
import com.example.domain.CustomerOrderEvent;
import com.example.integration.InventoryOrderEvent;
import com.example.repository.CustomerRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class InventoryListener {

    private Logger logger = LoggerFactory.getLogger(InventoryListener.class);

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private CustomerClient customerClient;

    @Topic("inventory")
    public void receive(@Body InventoryOrderEvent event) {
        switch (event.getType()) {
            case ORDER_RESERVED_FROM_INVENTORY -> {
                var order = event.getOrder();
                logger.info("RECEIVED EVENT: " + event.getType());
                var customerOpt = customerRepository.findById(order.getCustomerId());
                customerOpt.ifPresentOrElse(customer -> {
                    var funds = customer.getCredit();
                    var price = order.getTotalPrice();
                    if (price <= funds) {
                        customer.setCredit(funds - price);
                        customerRepository.update(customer);
                        var customerOrderEvent = new CustomerOrderEvent(EventType.ORDER_PAID, customer, order);
                        customerClient.sendCustomerOrderEvent(customerOrderEvent);
                        logger.info("ORDER_PAID: " + customer);
                    } else {
                        logger.info("NOT ENOUGH CREDITS: " + customer);
                    }
                }, () -> logger.info("CUSTOMER " + order.getCustomerId() + " NOT FOUND"));
            }
            case ORDER_ITEM_OUT_OF_STOCK -> {}
        }
    }

}
