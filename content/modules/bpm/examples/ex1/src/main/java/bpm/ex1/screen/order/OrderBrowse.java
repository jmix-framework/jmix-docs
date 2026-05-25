package bpm.ex1.screen.order;

import io.jmix.bpmui.processform.ProcessFormScreens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import bpm.ex1.entity.Order;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("smpl_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {

    // tag::start-form[]
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    protected ProcessFormScreens processFormScreens;

    @Subscribe("startProcBtn")
    public void onStartProcBtnClick(Button.ClickEvent event) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery() // <1>
                .processDefinitionKey("order-process")
                .latestVersion()
                .singleResult();

        Screen startProcessForm = processFormScreens.createStartProcessForm(processDefinition, this); // <2>
        startProcessForm.show(); // <3>
    }
    // end::start-form[]

}