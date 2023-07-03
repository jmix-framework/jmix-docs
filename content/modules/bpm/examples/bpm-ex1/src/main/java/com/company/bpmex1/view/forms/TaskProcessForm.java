package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.Order;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.*;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("TaskProcessForm")
@ViewDescriptor("task-process-form.xml")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        }
)
public class TaskProcessForm extends StandardView {

    @ViewComponent
    @ProcessVariable(name = "comment")
    private TypedTextField<String> commentField;

    @ViewComponent
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;

    // tag::complete-task1[]
    @Autowired
    private ProcessFormContext processFormContext;

    // end::complete-task1[]

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    // tag::complete-task2[]
    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion() // <1>
                .withOutcome("reject") // <2>
                .saveInjectedProcessVariables() // <3>
                .complete(); // <4>
        closeWithDefaultAction(); // <5>
    }
    // end::complete-task2[]
}