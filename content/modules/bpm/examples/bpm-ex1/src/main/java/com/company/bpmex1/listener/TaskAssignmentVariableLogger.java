package com.company.bpmex1.listener;

import com.company.bpmex1.entity.Order;
import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import org.flowable.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignmentVariableLogger {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentVariableLogger.class);

    // tag::get-variable-1[]
    @Autowired
    private RuntimeService runtimeService;

    @EventListener
    public void onTaskAssigned(UserTaskAssignedEvent event) {
        Order order = (Order) runtimeService.getVariable(event.getTask().getExecutionId(), "order");
        // ...
        // end::get-variable-1[]
        log.info("Task '{}' for order {} is assigned to {}",
                event.getTask().getName(), order != null ? order.getNumber() : null, event.getUsername());
        // tag::get-variable-2[]
    }
    // end::get-variable-2[]
}
