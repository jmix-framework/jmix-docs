package bpm.ex1.screen.forms;

import bpm.ex1.service.AcceptsTask;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom[]
@UiController("smpl_CustomTaskForm")
@UiDescriptor("custom-task-form.xml")
public class CustomTaskForm extends Screen implements AcceptsTask {

    private Task task;

    @Autowired
    private TaskService taskService;

    @Subscribe("completeTaskBtn")
    public void onCompleteTaskBtnClick(Button.ClickEvent event) {
        taskService.complete(task.getId());
        closeWithDefaultAction();
    }

    @Override
    public void setTask(Task task) {
        this.task = task;
    }
}
// end::custom[]