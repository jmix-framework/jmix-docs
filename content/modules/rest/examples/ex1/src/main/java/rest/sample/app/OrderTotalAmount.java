package rest.sample.app;

import java.math.BigDecimal;

public class OrderTotalAmount {
    private final BigDecimal totalAmount;
    private final int orderId;


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public OrderTotalAmount(BigDecimal totalAmount, int orderId) {
        this.totalAmount = totalAmount;
        this.orderId = orderId;
    }
}
