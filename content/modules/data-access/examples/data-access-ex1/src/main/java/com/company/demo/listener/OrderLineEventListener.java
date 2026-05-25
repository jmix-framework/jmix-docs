package com.company.demo.listener;

import com.company.demo.entity.Order;
import com.company.demo.entity.OrderLine;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

// tag::entity-changed[]
@Component
public class OrderLineEventListener {

    @Autowired
    private DataManager dataManager;

    @EventListener
    void onOrderLineChangedBeforeCommit(EntityChangedEvent<OrderLine> event) {
        Order order;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {               // <1>
            Id<Order> orderId = event.getChanges().getOldReferenceId("order");  // <2>
            order = dataManager.load(orderId).one();
        } else {
            OrderLine orderLine = dataManager.load(event.getEntityId()).one();
            order = orderLine.getOrder();
        }
        BigDecimal amount = order.getLines().stream()
                .map(line -> line.getProduct().getPrice().multiply(
                        BigDecimal.valueOf(line.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setAmount(amount);
        dataManager.save(order);
    }
}
// end::entity-changed[]
