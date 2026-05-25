package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.core.TimeSource;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("smpl_StartProcessForm1")
@UiDescriptor("start-process-form1.xml")
// tag::allowed-process[]
@ProcessForm(allowedProcessKeys = {"process-1", "process-2"})
// end::allowed-process[]
public class StartProcessForm1 extends Screen {

    @Autowired
    private TimeSource timeSource;

    // tag::start-process[]
    // tag::variables[]
    @ProcessVariable
    private Date date;

    @Autowired
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;
    // end::variables[]

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting() //<1>
                .withBusinessKey("order-1") //<2>
                .addProcessVariable("date", date)
                .addProcessVariable("order", orderEntityPicker.getValue()) //<3>
                .start(); //<4>
        closeWithDefaultAction(); //<5>
    }
    // end::start-process[]

    @Subscribe
    public void onInit(InitEvent event) {
        date=timeSource.currentTimestamp();
    }
}