package com.company.bpmex1.listener;

import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskAssignmentSpelLogger {

    private static final Logger log = LoggerFactory.getLogger(TaskAssignmentSpelLogger.class);

    // tag::spel-example-1[]
    @EventListener(condition = "#event.processDefinition.key == 'order-approval'")
    protected void onOrderApprovalTaskAssigned(UserTaskAssignedEvent event) {
        // ...
        // end::spel-example-1[]
        log.info("Order approval task '{}' is assigned to {} (SpEL condition)",
                event.getTask().getName(), event.getUsername());
        // tag::spel-example-2[]
    }
    // end::spel-example-2[]
}
