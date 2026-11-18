package com.company.demo.sales;

import com.company.demo.entity.Order;
import io.jmix.core.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

    @Autowired
    private DataManager dataManager;

    public Order process(Order order) {
        // tag::mdc[]
        try (MDC.MDCCloseable ignored = MDC.putCloseable("orderNumber", order.getNumber())) {
            log.info("Confirming order");
            return dataManager.save(order); // <1>
        }
        // end::mdc[]
    }
}
