package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.Order;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.OutputVariable;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("OutputVariablesForm")
@ViewDescriptor("output-variables-form.xml")
// tag::output-variables[]
@ProcessForm(
        outputVariables = {
                @OutputVariable(name = "order", type = Order.class),
                @OutputVariable(name = "comment", type = String.class)
        }
)
// end::output-variables[]
public class OutputVariablesForm extends StandardView {

    @ViewComponent
    @ProcessVariable(name = "comment")
    private TypedTextField<String> commentField;

    @ViewComponent
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.processStarting()
                .withBusinessKey(orderEntityPicker.getValue().getNumber())
                .saveInjectedProcessVariables()
                .start();
        closeWithDefaultAction();
    }
}