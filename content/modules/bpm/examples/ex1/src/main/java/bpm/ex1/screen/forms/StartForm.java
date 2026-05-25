package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_StartFormExample")
@UiDescriptor("start-form.xml")
// tag::start-form[]
@ProcessForm
public class StartForm extends Screen {

    @Autowired
    private EntityPicker<Order> orderEntityPicker;

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting() // <1>
                .addProcessVariable("order", orderEntityPicker.getValue())
                .start();
        closeWithDefaultAction();
    }
}
// end::start-form[]