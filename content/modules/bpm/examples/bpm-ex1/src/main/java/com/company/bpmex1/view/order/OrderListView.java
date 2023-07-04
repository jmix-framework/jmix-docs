package com.company.bpmex1.view.order;

import com.company.bpmex1.entity.Order;

import com.company.bpmex1.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bpmflowui.processform.ProcessFormViews;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "orders", layout = MainView.class)
@ViewController("smpl_Order.list")
@ViewDescriptor("order-list-view.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView extends StandardListView<Order> {

    // tag::start-form[]
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    protected ProcessFormViews processFormViews;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(final ClickEvent<JmixButton> event) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // <1>
                .processDefinitionKey("order-process")
                .latestVersion()
                .singleResult();

        processFormViews.openStartProcessForm(processDefinition, this); // <2>
    }
    // end::start-form[]
}