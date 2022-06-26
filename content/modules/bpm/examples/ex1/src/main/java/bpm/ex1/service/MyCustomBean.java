package bpm.ex1.service;

import bpm.ex1.entity.User;
import io.jmix.bpm.service.BpmTaskService;
import io.jmix.core.security.CurrentAuthentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//tag::bean[]
@Component("sample_MyCustomBean")
public class MyCustomBean {

    //end::bean[]

    // tag::runtime-service[]
    @Autowired
    private RuntimeService runtimeService;

    // end::runtime-service[]

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MyCustomBean.class);

    // tag::task-service[]
    @Autowired
    private TaskService taskService;

    // end::task-service[]

    @Autowired
    private BpmTaskService bpmTaskService;

    // tag::current-authentication[]
    @Autowired
    private CurrentAuthentication currentAuthentication;

    // end::current-authentication[]

    //tag::get-tasks[]
    public List<Task> getCurrentUserTasks() {
        return taskService.createTaskQuery() // <1>
                .processDefinitionKey("approval") // <2>
                .taskAssignee(currentAuthentication.getUser().getUsername()) // <3>
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list();
    }
    //end::get-tasks[]

    // tag::get-instances[]
    public List<ProcessInstance> getActiveProcessInstances() {
        return runtimeService.createProcessInstanceQuery() // <1>
                .processDefinitionKey("approval") // <2>
                .variableValueEquals("orderId", "N-1") // <3>
                .active()
                .list();
    }
    // end::get-instances[]

    public void showMethods() {
        for (int i = 0; i < getCurrentUserTasks().size(); i++) {
            log.info(getCurrentUserTasks().get(i).getName());
        }
        for (int i = 0; i < getActiveProcessInstances().size(); i++) {
            log.info(getActiveProcessInstances().get(i).getProcessDefinitionKey());
        }
    }

    public String getEmail(User user){
        return user.getEmail();
    }
}