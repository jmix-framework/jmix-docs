package com.company.bpmex1.view.forms;

import com.company.bpmex1.entity.Order;
import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpm.service.BpmModelService;
import io.jmix.bpmflowui.processform.ProcessFormContext;
import io.jmix.bpmflowui.processform.annotation.ProcessForm;
import io.jmix.bpmflowui.processform.annotation.ProcessVariable;
import io.jmix.flowui.component.textarea.JmixTextArea;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("sample_ExtensionTaskForm")
@ViewDescriptor("extension-task-form.xml")
@ProcessForm
public class ExtensionTaskForm extends StandardView {

    @Autowired
    private RepositoryService repositoryService;

    @ViewComponent
    @ProcessVariable(name = "order")
    private EntityPicker<Order> orderEntityPicker;

    @ViewComponent
    @ProcessVariable(name = "comment")
    private JmixTextArea orderComment;

    // tag::get-property[]
    @Autowired
    private ProcessFormContext processFormContext;

    @Autowired
    private BpmModelService bpmModelService;

    @ViewComponent
    private TypedTextField<String> priority;

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
    public void onCompleteTaskBtnClick(ClickEvent<JmixButton> event) {
        processFormContext.taskCompletion()
                .saveInjectedProcessVariables()
                .complete();
        closeWithDefaultAction();
    }

}