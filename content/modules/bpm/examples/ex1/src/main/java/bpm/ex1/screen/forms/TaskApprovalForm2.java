package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import bpm.ex1.entity.User;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Outcome;
import io.jmix.bpmui.processform.annotation.OutputVariable;
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
import org.springframework.security.core.parameters.P;

@UiController("smpl_TaskApprovalForm2")
@UiDescriptor("task-approval-form2.xml")
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
public class TaskApprovalForm2 extends Screen {

    @Autowired
    @ProcessVariable(name = "comment")
    private TextField<String> commentField;

    @Autowired
    @ProcessVariable
    private TextField<String> rejectionReason;

    @Autowired
    @ProcessVariable(name = "nextActor")
    private EntityPicker<User> userEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("reject")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

}