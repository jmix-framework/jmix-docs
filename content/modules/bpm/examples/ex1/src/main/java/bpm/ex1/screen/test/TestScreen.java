package bpm.ex1.screen.test;

import bpm.ex1.service.MyCustomBean;
import io.jmix.bpmui.processform.ProcessFormScreens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_TestScreen")
@UiDescriptor("test-screen.xml")
public class TestScreen extends Screen {

    @Autowired
    private MyCustomBean myCustomBean;
    // tag::task-form[]
    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessFormScreens processFormScreens;

    @Subscribe("openTaskBtn")
    public void onOpenTaskBtnClick(Button.ClickEvent event) {

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("approve-order-process")
                .taskAssignee("admin")
                .active()
                .orderByTaskCreateTime()
                .list()
                .get(0);

        Screen taskProcessForm = processFormScreens.createTaskProcessForm(task, this);
        taskProcessForm.show();
    }
    // end::task-form[]

    @Subscribe("testListsBtn")
    public void onTestListsBtnClick(Button.ClickEvent event) {
        myCustomBean.showMethods();
    }

}