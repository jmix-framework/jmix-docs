package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import io.jmix.bpmui.processform.ProcessFormContext;
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

@UiController("smpl_StartProcessForm3")
@UiDescriptor("start-process-form3.xml")
// tag::output-variables[]
@ProcessForm(
        outputVariables = {
                @OutputVariable(name = "order", type = Order.class),
                @OutputVariable(name = "comment", type = String.class)
        }
)
// end::output-variables[]
public class StartProcessForm3 extends Screen {

    @Autowired
    @ProcessVariable(name = "comment")
    private TextField<String> commentField;

    @Autowired
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting()
                .withBusinessKey(orderEntityPicker.getValue().getNumber())
                .saveInjectedProcessVariables()
                .start();
        closeWithDefaultAction();
    }
}