package bpm.ex1.screen.forms;

import bpm.ex1.entity.User;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("smpl_StartProcessForm2")
@UiDescriptor("start-process-form2.xml")
@ProcessForm
public class StartProcessForm2 extends Screen {

    @Autowired
    @ProcessVariable
    private TextField<String> orderNumber;

    @Autowired
    @ProcessVariable(name = "manager")
    private EntityPicker<User> managerEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;


    // tag::start-example[]
    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting()
                .withBusinessKey(orderNumber.getValue())
                .addProcessVariable("orderNumber", orderNumber.getValue())
                .addProcessVariable("manager",managerEntityPicker.getValue())
                .start();
        closeWithDefaultAction();
    }
    // end::start-example[]
}
