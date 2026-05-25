package com.company.bpmex1.view.order;

import com.company.bpmex1.entity.Order;

import com.company.bpmex1.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Route(value = "orders/:id", layout = MainView.class)
@ViewController("smpl_Order.detail")
@ViewDescriptor("order-detail-view.xml")
@EditedEntityContainer("orderDc")
public class OrderDetailView extends StandardDetailView<Order> {
    // tag::run-process[]
    @Autowired
    private RuntimeService runtimeService;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(final ClickEvent<JmixButton> event) {
        Order order = getEditedEntity();
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("order", order); // <1>
        runtimeService.startProcessInstanceByKey("order-approval", // <2>
                order.getNumber(),
                processVariables);
        closeWithSave();
    }
    // end::run-process[]
}