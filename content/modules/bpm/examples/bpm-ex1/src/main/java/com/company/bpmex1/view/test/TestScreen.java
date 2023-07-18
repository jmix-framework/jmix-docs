package com.company.bpmex1.view.test;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.bpmflowui.processform.ProcessFormViews;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;

@ViewController("sample_TestScreen")
@ViewDescriptor("test-screen.xml")
public class TestScreen extends StandardView {

    // tag::task-form[]
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessFormViews processFormViews;

    @Subscribe("openTaskBtn")
    public void onOpenTaskBtnClick(ClickEvent<JmixButton> event) {

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("approve-order-process")
                .taskAssignee("admin")
                .active()
                .orderByTaskCreateTime()
                .list()
                .get(0);

        processFormViews.openTaskProcessForm(task, this);
    }
    // end::task-form[]

}