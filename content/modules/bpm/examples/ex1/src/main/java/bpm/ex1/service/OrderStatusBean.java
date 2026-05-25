package bpm.ex1.service;

import bpm.ex1.entity.Order;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(OrderStatusBean.NAME)
public class OrderStatusBean implements OrderStatus {
    public static final String NAME = "smpl_OrderStatusBean";
    @Autowired
    private DataManager dataManager;

    @Override
    public void setStatus(Order order, String status) {
        Order reloadedOrder = dataManager.load(Order.class)
                .id(order.getId())
                .one();
        reloadedOrder.setStatus(status);
        dataManager.save(reloadedOrder);
    }
}