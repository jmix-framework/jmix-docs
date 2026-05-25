package bpm.ex1.screen.forms;

import bpm.ex1.entity.Order;
import io.jmix.bpm.service.BpmModelService;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessVariable;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_ExtensionTaskForm")
@UiDescriptor("extension-task-form.xml")
@ProcessForm
public class ExtensionTaskForm extends Screen {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;

    @Autowired
    @ProcessVariable(name = "comment")
    private TextArea<String> orderComment;

    // tag::get-property[]
    @Autowired
    private ProcessFormContext processFormContext;

    @Autowired
    private BpmModelService bpmModelService;

    @Autowired
    private TextField<String> priority;

    @Subscribe
    public void onInit(InitEvent event) {
        String processDefinitionId = processFormContext.getTask().getProcessDefinitionId(); // <1>
        String elementId = processFormContext.getTask().getTaskDefinitionKey();

        String priorityValue = bpmModelService.getElementExtensionProperties(processDefinitionId, elementId)
                .get(0)
                .getValue(); // <2>

        priority.setValue(priorityValue); // <3>
    }
    // end::get-property[]

    @Subscribe("completeTaskBtn")
    public void onCompleteTaskBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

}