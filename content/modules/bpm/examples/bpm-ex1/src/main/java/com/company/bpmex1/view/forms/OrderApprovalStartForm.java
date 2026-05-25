package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.User;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::start-example[]
@ViewController("OrderApprovalStartForm")
@ViewDescriptor("order-approval-start-form.xml")
@ProcessForm // <1>
public class OrderApprovalStartForm extends StandardView {

    @ViewComponent
    @ProcessVariable // <2>
    private TypedTextField<String> orderNumber;

    @ViewComponent
    @ProcessVariable(name = "manager") // <3>
    private EntityPicker<User> managerEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext; // <4>

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.processStarting()
                .withBusinessKey(orderNumber.getValue()) // <5>
                .saveInjectedProcessVariables() // <6>
                .start();
        closeWithDefaultAction();
    }
}
// end::start-example[]