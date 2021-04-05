package bpm.ex1.screen.forms;

import bpm.ex1.service.AcceptsTaskData;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.flowable.engine.TaskService;
import io.jmix.bpm.entity.TaskData;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom[]
@UiController("smpl_CustomTaskForm")
@UiDescriptor("custom-task-form.xml")
public class CustomTaskForm extends Screen implements AcceptsTaskData {

    private TaskData taskData;

    @Autowired
    private TaskService taskService;

    @Subscribe("completeTaskBtn")
    public void onCompleteTaskBtnClick(Button.ClickEvent event) {
        taskService.complete(taskData.getId());
        closeWithDefaultAction();
    }

    @Override
    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }
}
// end::custom[]