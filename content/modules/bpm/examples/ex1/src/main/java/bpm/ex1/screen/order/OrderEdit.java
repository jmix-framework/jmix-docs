package bpm.ex1.screen.order;

import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import bpm.ex1.entity.Order;
import org.flowable.engine.RuntimeService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@UiController("smpl_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@ProcessForm
public class OrderEdit extends StandardEditor<Order> {
    // tag::run-process[]
    @Inject
    private RuntimeService runtimeService;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        Order order = getEditedEntity();
        Map<String, Object> processVariables = new HashMap<>();
        processVariables.put("order", order); // <1>
        runtimeService.startProcessInstanceByKey("order-approval", // <2>
                order.getNumber(),
                processVariables);
        closeWithCommit();
    }
    // end::run-process[]
}