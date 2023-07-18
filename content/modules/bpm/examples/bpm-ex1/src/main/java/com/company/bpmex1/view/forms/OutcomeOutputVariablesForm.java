package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.User;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.Outcome;
import io.jmix.bpmflowui.processform.annotation.OutputVariable;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("OutcomeOutputVariablesForm")
@ViewDescriptor("outcome-output-variables-form.xml")
// tag::output-variables[]
@ProcessForm(
   outcomes = {
      @Outcome(
         id = "approve",
         outputVariables = {
            @OutputVariable(name = "nextActor", type = User.class) // <1>
         }
      ),
      @Outcome(
        id = "reject",
           outputVariables = {
              @OutputVariable(name = "rejectionReason", type = String.class) // <2>
        }
      )
   },
   outputVariables = {
      @OutputVariable(name = "comment", type = String.class) // <3>
   }
)
// end::output-variables[]
public class OutcomeOutputVariablesForm extends StandardView {

    @ViewComponent
    @ProcessVariable(name = "comment")
    private TypedTextField<String> commentField;

    @ViewComponent
    @ProcessVariable
    private TypedTextField<String> rejectionReason;

    @ViewComponent
    @ProcessVariable(name = "nextActor")
    private EntityPicker<User> userEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .withOutcome("reject")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

}