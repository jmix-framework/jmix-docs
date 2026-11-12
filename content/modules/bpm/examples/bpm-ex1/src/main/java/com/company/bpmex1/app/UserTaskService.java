package com.company.bpmex1.app;

import io.jmix.core.security.CurrentAuthentication;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
public class UserTaskService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CurrentAuthentication currentAuthentication;

    // tag::due-date[]
    public void postponeDueDate(String taskId, Duration delay) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        Date dueDate = task.getDueDate();
        if (dueDate != null) {
            task.setDueDate(Date.from(dueDate.toInstant().plus(delay)));
            taskService.saveTask(task);
        }
    }
    // end::due-date[]

    // tag::set-assignee[]
    public void assign(String taskId, String username) {
        taskService.setAssignee(taskId, username);
    }
    // end::set-assignee[]

    // tag::assigned-tasks[]
    public List<Task> getAssignedTasks(String username) {
        return taskService.createTaskQuery()
                .taskAssignee(username)
                .list();
    }
    // end::assigned-tasks[]

    // tag::claim[]
    public void claim(String taskId) {
        taskService.claim(taskId, currentAuthentication.getUser().getUsername()); // <1>
    }
    // end::claim[]
}
