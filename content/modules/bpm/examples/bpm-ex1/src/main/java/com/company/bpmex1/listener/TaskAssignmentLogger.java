package com.company.bpmex1.listener;

import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// tag::event-listener-1[]
@Component
public class TaskAssignmentLogger {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentLogger.class);

    @EventListener
    public void onTaskAssigned(UserTaskAssignedEvent event) {
        Task task = event.getTask(); // <1>
        log.info("Task '{}' of process '{}' is assigned to {}",
                task.getName(),
                event.getProcessDefinition().getKey(), // <2>
                event.getUsername()); // <3>
    }
    // end::event-listener-1[]

    // tag::specific-process-1[]
    @EventListener
    protected void onOrderApprovalTaskAssigned(UserTaskAssignedEvent event) {
        if ("order-approval".equals(event.getProcessDefinition().getKey())) {
            // ...
            // end::specific-process-1[]
            log.info("Order approval task '{}' is assigned to {}", event.getTask().getName(), event.getUsername());
            // tag::specific-process-2[]
        }
    }
    // end::specific-process-2[]
    // tag::event-listener-2[]
}
// end::event-listener-2[]
