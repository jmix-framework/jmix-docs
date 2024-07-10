package com.company.demo.listener;

import com.company.demo.entity.Order;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

// tag::saving-event[]
@Component
public class OrderEventListener {

    @EventListener
    void onOrderSaving(EntitySavingEvent<Order> event) {
        if (event.isNewEntity()) {
            Order order = event.getEntity();
            order.setNumber(generateOrderNumber());
        }
    }
    // end::saving-event[]

    private AtomicLong orderNumber = new AtomicLong();

    private String generateOrderNumber() {
        return String.valueOf(orderNumber.incrementAndGet());
    }
}
