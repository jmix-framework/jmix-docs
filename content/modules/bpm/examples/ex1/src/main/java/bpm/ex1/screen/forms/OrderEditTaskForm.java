package bpm.ex1.screen.forms;

import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import bpm.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

// tag::task-form[]
@UiController("smpl_OrderEditTaskForm")
@UiDescriptor("order-edit-task-form.xml")
@EditedEntityContainer("orderDc")
@ProcessForm // <1>
public class OrderEditTaskForm extends StandardEditor<Order> {

    @ProcessVariable
    protected Order order; // <2>

    @Autowired
    protected ProcessFormContext processFormContext;

    @Subscribe
    protected void onInit(InitEvent event) {
        setEntityToEdit(order); // <3>
    }

    @Subscribe("completeTaskBtn")
    protected void onCompleteTaskBtnClick(Button.ClickEvent event) {
        commitChanges() // <4>
                .then(() -> {
                    processFormContext.taskCompletion()
                            .complete();
                    closeWithDefaultAction();
                });
    }
    // end::task-form[]
}