package com.company.bpmex1.view.forms;

import com.vaadin.flow.component.ClickEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom[]
@ViewController("CustomTaskForm")
@ViewDescriptor("custom-task-form.xml")
public class CustomTaskForm extends StandardView implements AcceptsTask {

    private Task task;

    @Autowired
    private TaskService taskService;

    @Subscribe("completeTaskBtn")
    public void onCompleteTaskBtnClick(ClickEvent<JmixButton> event) {
        taskService.complete(task.getId());
        closeWithDefaultAction();
    }

    @Override
    public void setTask(Task task) {
        this.task = task;
    }
}
// end::custom[]