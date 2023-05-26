package testing.ex1.listener;

import testing.ex1.entity.Order;
import testing.ex1.entity.OrderLine;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import testing.ex1.order.OrderAmountCalculation;

import java.math.BigDecimal;

@Component
public class OrderLineEventListener {

    @Autowired
    private DataManager dataManager;

    // tag::recalculate-order-amount[]
    @EventListener
    public void recalculateOrderAmount(EntityChangedEvent<OrderLine> event) {
        Order order = findOrderFromEvent(event);

        BigDecimal amount = new OrderAmountCalculation().calculateTotalAmount(order.getLines());
        order.setAmount(amount);

        dataManager.save(order);
    }
    // end::recalculate-order-amount[]

    private Order findOrderFromEvent(EntityChangedEvent<OrderLine> event) {
        Order order;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Order> orderId = event.getChanges().getOldReferenceId("order");
            order = dataManager.load(orderId).one();
        } else {
            OrderLine orderLine = dataManager.load(event.getEntityId()).one();
            order = orderLine.getOrder();
        }
        return order;
    }
}
