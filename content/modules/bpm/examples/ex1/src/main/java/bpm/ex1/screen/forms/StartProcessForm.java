package bpm.ex1.screen.forms;

import bpm.ex1.entity.Assistant;
import bpm.ex1.entity.Order;
import bpm.ex1.entity.User;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.core.TimeSource;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

// tag::start-example[]
@UiController("smpl_StartProcessForm")
@UiDescriptor("start-process-form.xml")
@ProcessForm // <1>
public class StartProcessForm extends Screen {

    @Autowired
    @ProcessVariable // <2>
    private TextField<String> orderNumber;

    @Autowired
    @ProcessVariable(name = "manager") // <3>
    private EntityPicker<User> managerEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext; // <4>

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting()
                .withBusinessKey(orderNumber.getValue()) // <5>
                .saveInjectedProcessVariables() // <6>
                .start();
        closeWithDefaultAction();
    }
}
// end::start-example[]