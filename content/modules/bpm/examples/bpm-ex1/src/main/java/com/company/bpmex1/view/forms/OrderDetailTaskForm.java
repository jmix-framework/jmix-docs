package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.Order;
import com.company.bpmex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::task-form[]
@Route(value = "order-form", layout = MainView.class)
@ViewController("OrderDetailTaskForm")
@ViewDescriptor("order-detail-task-form.xml")
@EditedEntityContainer("orderDc")
@ProcessForm // <1>
public class OrderDetailTaskForm extends StandardDetailView<Order> {

    @ProcessVariable
    protected Order order; // <2>

    @Autowired
    protected ProcessFormContext processFormContext;

//    @Subscribe
//    protected void onInit(View.InitEvent event) {
//        setEntityToEdit(order); // <3>
//    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        setEntityToEdit(order); // <3>
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        setEntityToEdit(order); // <3>
    }

    @Subscribe("completeTaskBtn")
    protected void onCompleteTaskBtnClick(ClickEvent<JmixButton> event) {
        save() // <4>
                .then(() -> {
                    processFormContext.taskCompletion()
                            .complete();
                    closeWithDefaultAction();
                });
    }
    // end::task-form[]
}