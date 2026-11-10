package com.company.bpmex1.app;

import com.company.bpmex1.entity.Order;
import io.jmix.bpm.service.BpmTaskService;
import io.jmix.core.security.CurrentAuthentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// tag::bean[]
@Component
public class OrderApprovalService {

    // end::bean[]
    // tag::runtime-service[]
    @Autowired
    private RuntimeService runtimeService;

    // end::runtime-service[]
    // tag::task-service[]
    @Autowired
    private TaskService taskService;

    // end::task-service[]
    // tag::current-authentication[]
    @Autowired
    private CurrentAuthentication currentAuthentication;

    // end::current-authentication[]
    // tag::bpm-task-service[]
    @Autowired
    private BpmTaskService bpmTaskService;

    // end::bpm-task-service[]
    // tag::get-tasks[]
    public List<Task> getMyApprovalTasks() {
        return taskService.createTaskQuery() // <1>
                .processDefinitionKey("order-approval") // <2>
                .taskAssignee(currentAuthentication.getUser().getUsername()) // <3>
                .active()
                .orderByTaskCreateTime()
                .desc()
                .list();
    }
    // end::get-tasks[]

    // tag::get-instances[]
    public List<ProcessInstance> getActiveApprovals(Order order) {
        return runtimeService.createProcessInstanceQuery() // <1>
                .processDefinitionKey("order-approval") // <2>
                .processInstanceBusinessKey(order.getNumber()) // <3>
                .active()
                .list();
    }
    // end::get-instances[]

    // tag::complete-task[]
    public void approve(Task task, String comment) {
        Map<String, Object> processVariables = new HashMap<>(); // <1>
        processVariables.put("comment", comment);
        bpmTaskService.completeTaskWithOutcome(task.getId(), "approve", processVariables); // <2>
    }
    // end::complete-task[]

    // tag::update-business-key[]
    public void updateBusinessKey(ProcessInstance processInstance, Order order) {
        runtimeService.updateBusinessKey(processInstance.getId(), order.getNumber());
    }
    // end::update-business-key[]
}
