package com.company.bpmex1.app;

import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.eventsubscription.api.EventSubscription;
import org.flowable.job.api.Job;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Checks the examples of the Events page (bpm/pages/bpmn/bpmn-events.adoc):
 * querying message subscriptions and timer jobs, sending a message, throwing a signal
 * and the MessageSender bean of the message throwing event workaround.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class ProcessEventServiceTest {

    @Autowired
    ProcessEventService processEventService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    Deployment deployment;

    @BeforeEach
    void setUp() {
        deployment = repositoryService.createDeployment()
                .name("process-event-service-test")
                .addString("payment-wait.bpmn20.xml", PAYMENT_WAIT_XML)
                .addString("shop-wait.bpmn20.xml", SHOP_WAIT_XML)
                .addString("timer-wait.bpmn20.xml", TIMER_WAIT_XML)
                .addString("message-sender.bpmn20.xml", MESSAGE_SENDER_XML)
                .deploy();
    }

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void given_processWaitsForMessage_when_getMessageSubscriptions_then_subscriptionFound() {
        // given:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("payment-wait");

        // when:
        List<EventSubscription> subscriptions = processEventService.getMessageSubscriptions("Payment received");

        // then:
        assertThat(subscriptions)
                .extracting(EventSubscription::getProcessInstanceId)
                .contains(processInstance.getId());
    }

    @Test
    void given_processWaitsAtTimer_when_getTimerJobs_then_jobDueInOneHour() {
        // given:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("timer-wait");

        // when:
        List<Job> timerJobs = processEventService.getTimerJobs(processInstance.getId());

        // then:
        assertThat(timerJobs).hasSize(1);
        Instant dueDate = timerJobs.get(0).getDuedate().toInstant();
        assertThat(dueDate).isBetween(
                Instant.now().plus(Duration.ofMinutes(59)),
                Instant.now().plus(Duration.ofMinutes(61)));
    }

    @Test
    void given_processWaitsForMessage_when_confirmPayment_then_processContinues() {
        // given:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("payment-wait");

        // when:
        processEventService.confirmPayment(processInstance.getId());

        // then:
        assertThat(currentTaskKey(processInstance)).isEqualTo("afterPayment");
        assertThat(processEventService.getMessageSubscriptions("Payment received"))
                .extracting(EventSubscription::getProcessInstanceId)
                .doesNotContain(processInstance.getId());
    }

    @Test
    void given_processWaitsForSignal_when_closeShop_then_processContinues() {
        // given:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("shop-wait");

        // when:
        processEventService.closeShop();

        // then:
        assertThat(currentTaskKey(processInstance)).isEqualTo("afterSignal");
    }

    @Test
    void given_receiverWaitsForMessage_when_senderProcessRuns_then_receiverContinues() {
        // given: the receiver and the sender have the same business key
        String orderNumber = "event-test-" + UUID.randomUUID();
        ProcessInstance receiver = runtimeService.startProcessInstanceByKey("payment-wait", orderNumber);

        // when:
        runtimeService.startProcessInstanceByKey("message-sender", orderNumber,
                Map.of("orderNumber", orderNumber));

        // then:
        assertThat(currentTaskKey(receiver)).isEqualTo("afterPayment");
    }

    @Test
    void given_noReceiver_when_senderProcessRuns_then_startFails() {
        // given:
        String orderNumber = "event-test-" + UUID.randomUUID();

        // when, then: the exception from the bean fails the start of the sender process
        assertThatThrownBy(() -> runtimeService.startProcessInstanceByKey("message-sender", orderNumber,
                Map.of("orderNumber", orderNumber)))
                .hasRootCauseInstanceOf(IllegalStateException.class);
    }

    String currentTaskKey(ProcessInstance processInstance) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        return task == null ? null : task.getTaskDefinitionKey();
    }

    static final String PAYMENT_WAIT_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         targetNamespace="http://www.flowable.org/processdef">
              <message id="paymentReceived" name="Payment received"/>
              <process id="payment-wait" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="waitForPayment"/>
                <intermediateCatchEvent id="waitForPayment">
                  <messageEventDefinition messageRef="paymentReceived"/>
                </intermediateCatchEvent>
                <sequenceFlow id="flow2" sourceRef="waitForPayment" targetRef="afterPayment"/>
                <userTask id="afterPayment"/>
                <sequenceFlow id="flow3" sourceRef="afterPayment" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;

    static final String SHOP_WAIT_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         targetNamespace="http://www.flowable.org/processdef">
              <signal id="shopClosed" name="Shop closed"/>
              <process id="shop-wait" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="waitForSignal"/>
                <intermediateCatchEvent id="waitForSignal">
                  <signalEventDefinition signalRef="shopClosed"/>
                </intermediateCatchEvent>
                <sequenceFlow id="flow2" sourceRef="waitForSignal" targetRef="afterSignal"/>
                <userTask id="afterSignal"/>
                <sequenceFlow id="flow3" sourceRef="afterSignal" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;

    static final String TIMER_WAIT_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         targetNamespace="http://www.flowable.org/processdef">
              <process id="timer-wait" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="waitOneHour"/>
                <intermediateCatchEvent id="waitOneHour">
                  <timerEventDefinition>
                    <timeDuration>PT1H</timeDuration>
                  </timerEventDefinition>
                </intermediateCatchEvent>
                <sequenceFlow id="flow2" sourceRef="waitOneHour" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;

    static final String MESSAGE_SENDER_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         xmlns:flowable="http://flowable.org/bpmn"
                         targetNamespace="http://www.flowable.org/processdef">
              <process id="message-sender" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="sendMessage"/>
                <serviceTask id="sendMessage" name="Send message"
                    flowable:expression="${MessageSender.sendMessage('Payment received', orderNumber)}"/>
                <sequenceFlow id="flow2" sourceRef="sendMessage" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;
}
