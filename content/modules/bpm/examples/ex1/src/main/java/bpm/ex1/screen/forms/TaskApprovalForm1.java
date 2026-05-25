package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import bpm.ex1.entity.User;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.*;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("smpl_TaskApprovalForm1")
@UiDescriptor("task-approval-form1.xml")
@ProcessForm(
        outcomes = {
                @Outcome(id = "approve"),
                @Outcome(id = "reject")
        }
)
public class TaskApprovalForm1 extends Screen {

    @Autowired
    @ProcessVariable(name = "comment")
    private TextField<String> commentField;

    @Autowired
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;

    // tag::complete-task1[]
    @Autowired
    private ProcessFormContext processFormContext;

    // end::complete-task1[]

    @Subscribe("approveBtn")
    protected void onApproveBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("approve")
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

    // tag::complete-task2[]
    @Subscribe("rejectBtn")
    protected void onRejectBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion() // <1>
                .withOutcome("reject") // <2>
                .saveInjectedProcessVariables() // <3>
                .complete(); // <4>
        closeWithDefaultAction(); // <5>
    }
    // end::complete-task2[]
}