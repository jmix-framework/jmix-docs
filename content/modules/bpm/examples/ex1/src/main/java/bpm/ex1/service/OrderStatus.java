package bpm.ex1.service;

import bpm.ex1.entity.Order;

public interface OrderStatus {
    void setStatus(Order order, String status);
}
