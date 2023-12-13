package com.company.demo.app;

import com.company.demo.entity.OrderLine;

import java.math.BigDecimal;
import java.util.List;

// tag::order-amount-calculation[]
public class OrderAmountCalculation {

    public BigDecimal calculateTotalAmount(List<OrderLine> orderLines) {

        return orderLines.stream()
                .map(this::totalPriceForOrderLine)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal totalPriceForOrderLine(OrderLine orderLine) {

        BigDecimal productPrice = orderLine.getProduct().getPrice();
        BigDecimal quantity = BigDecimal.valueOf(orderLine.getQuantity());

        return productPrice.multiply(quantity);
    }
}
// end::order-amount-calculation[]
