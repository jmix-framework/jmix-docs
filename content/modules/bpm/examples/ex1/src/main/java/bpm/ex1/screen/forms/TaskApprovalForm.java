package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Outcome;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

// tag::task-example[]
@UiController("smpl_TaskApprovalForm")
@UiDescriptor("task-approval-form.xml")
// tag::outcomes[]
@ProcessForm(
        outcomes = { // <1>
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        }
)
public class TaskApprovalForm extends Screen {

    // end::outcomes[]
    @Autowired
    @ProcessVariable // <2>
    private TextField<String> orderNumber;

    @Autowired
    @ProcessVariable(name = "comment") // <3>
    private TextField<String> commentField;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .saveInjectedProcessVariables() // <4>
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("reject")
                .addProcessVariable("comment", commentField.getValue()) // <5>
                .complete();
        closeWithDefaultAction();
    }
}
// end::task-example[]