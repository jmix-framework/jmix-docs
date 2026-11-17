package com.company.bpmex1.app;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// tag::message-sender[]
@Component("MessageSender")
public class MessageSender {

    @Autowired
    private RuntimeService runtimeService;

    public void sendMessage(String messageName, String businessKey) {
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceBusinessKey(businessKey, true) // <1>
                .messageEventSubscriptionName(messageName) // <2>
                .singleResult();
        if (execution == null) {
            throw new IllegalStateException(
                    "No process with business key " + businessKey + " waits for message " + messageName);
        }
        runtimeService.messageEventReceived(messageName, execution.getId());
    }
}
// end::message-sender[]
