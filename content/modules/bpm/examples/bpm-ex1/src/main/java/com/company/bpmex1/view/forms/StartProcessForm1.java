package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.Order;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@ViewController("smpl_StartProcessForm1")
@ViewDescriptor("start-process-form1.xml")
// tag::allowed-process[]
@ProcessForm(allowedProcessKeys = {"process-1", "process-2"})
// end::allowed-process[]
public class StartProcessForm1 extends StandardView {

    @Autowired
    private TimeSource timeSource;

    // tag::start-process[]
    // tag::variables[]
    @ProcessVariable
    private Date date;

    @ViewComponent
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;
    // end::variables[]

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(ClickEvent<JmixButton> event) {
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