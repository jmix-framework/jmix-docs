package com.company.demo.view.order;

import com.company.demo.entity.Order;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "orders-1", layout = MainView.class)
@ViewController("sample_Order.list1")
@ViewDescriptor("order-list-view-1.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView1 extends StandardListView<Order> {
}