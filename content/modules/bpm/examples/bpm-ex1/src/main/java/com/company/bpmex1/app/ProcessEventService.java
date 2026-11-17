package com.company.bpmex1.app;

import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.eventsubscription.api.EventSubscription;
import org.flowable.job.api.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessEventService {

    // tag::runtime-service[]
    @Autowired
    private RuntimeService runtimeService;

    // end::runtime-service[]
    // tag::management-service[]
    @Autowired
    private ManagementService managementService;

    // end::management-service[]
    // tag::message-subscriptions[]
    public List<EventSubscription> getMessageSubscriptions(String messageName) {
        return runtimeService.createEventSubscriptionQuery()
                .eventType("message") // <1>
                .eventName(messageName) // <2>
                .list();
    }
    // end::message-subscriptions[]

    // tag::timer-jobs[]
    public List<Job> getTimerJobs(String processInstanceId) {
        return managementService.createTimerJobQuery()
                .processInstanceId(processInstanceId)
                .orderByJobDuedate() // <1>
                .asc()
                .list();
    }
    // end::timer-jobs[]

    // tag::send-message[]
    public void confirmPayment(String processInstanceId) {
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId)
                .messageEventSubscriptionName("Payment received")
                .singleResult();
        runtimeService.messageEventReceived("Payment received", execution.getId());
    }
    // end::send-message[]

    // tag::throw-signal[]
    public void closeShop() {
        runtimeService.signalEventReceived("Shop closed");
    }
    // end::throw-signal[]
}
