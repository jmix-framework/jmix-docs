package com.company.demo.bean;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.entity.OrderLine;
import com.company.demo.repository.CustomerRepository;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.RemoveDelegate;
import io.jmix.core.SaveContext;
import io.jmix.core.SaveDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

// tag::class[]
@Component
public class OrderUpdateService implements SaveDelegate<Order>, RemoveDelegate<Order> {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EntityStates entityStates;

    @Override
    @Transactional
    public Order save(Order order, SaveContext saveContext) {   // <1>
        calculateTotalAmount(order);                            // <2>
        if (entityStates.isNew(order)) {                        // <3>
            incrementCustomerOrdersCount(order);
        }
        return dataManager.save(saveContext).get(order);        // <4>
    }

    @Override
    @Transactional
    public void remove(Order order) {                          // <5>
        decrementCustomerOrdersCount(order);
        dataManager.remove(order);
    }
    // end::class[]

    // tag::calculate-total[]
    private void calculateTotalAmount(Order order) {
        if (order.getLines() != null) {
            BigDecimal total = order.getLines().stream()
                    .map(this::getLineTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setAmount(total);
        }
    }

    private BigDecimal getLineTotal(OrderLine line) {
        if (line.getProduct() == null || line.getQuantity() == null) {
            return null;
        }
        return line.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(line.getQuantity()));
    }
    // end::calculate-total[]

    // tag::orders-count[]
    private void incrementCustomerOrdersCount(Order order) {
        // the related entity is reloaded because the instance held by
        // the order can be stale
        customerRepository.findById(order.getCustomer().getId()).ifPresent(customer -> {
            customer.setOrdersCount(getCurrentOrdersCount(customer) + 1);
            customerRepository.save(customer);
        });
    }

    private void decrementCustomerOrdersCount(Order order) {
        customerRepository.findById(order.getCustomer().getId()).ifPresent(customer -> {
            customer.setOrdersCount(getCurrentOrdersCount(customer) - 1);
            customerRepository.save(customer);
        });
    }

    private static int getCurrentOrdersCount(Customer customer) {
        return customer.getOrdersCount() == null ? 0 : customer.getOrdersCount();
    }
    // end::orders-count[]
}
