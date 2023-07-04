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

@ViewController("smpl_StartProcessForm2")
@ViewDescriptor("start-process-form2.xml")
@ProcessForm
public class StartProcessForm2 extends StandardView {

    @ViewComponent
    @ProcessVariable
    private TypedTextField<String> orderNumber;

    @ViewComponent
    @ProcessVariable(name = "manager")
    private EntityPicker<User> managerEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;


    // tag::start-example[]
    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.processStarting()
                .withBusinessKey(orderNumber.getValue())
                .addProcessVariable("orderNumber", orderNumber.getValue())
                .addProcessVariable("manager",managerEntityPicker.getValue())
                .start();
        closeWithDefaultAction();
    }
    // end::start-example[]
}
