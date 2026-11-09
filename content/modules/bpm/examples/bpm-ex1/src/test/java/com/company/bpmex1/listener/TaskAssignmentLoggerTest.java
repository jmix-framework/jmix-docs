package com.company.bpmex1.listener;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the listener examples of the Event Listeners page (bpm/pages/jmix-listeners.adoc):
 * the UserTaskAssignedEvent getters, reading a process variable, the process key check and the SpEL condition.
 * The test reads the messages that the listeners write to the log. It also collects the warnings that
 * the engine logs when a listener throws an exception, so a failing listener fails the test.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class TaskAssignmentLoggerTest {

    static final String ASSIGNEE = "alice";

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    Deployment deployment;

    final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();

    final List<Logger> loggers = List.of(
            (Logger) LoggerFactory.getLogger("com.company.bpmex1.listener"),
            (Logger) LoggerFactory.getLogger("org.flowable.common.engine.impl.event.FlowableEventSupport"));

    @BeforeEach
    void setUp() {
        deployment = repositoryService.createDeployment()
                .name("listener-test")
                .addString("order-approval.bpmn20.xml", processXml("order-approval"))
                .addString("other-process.bpmn20.xml", processXml("other-process"))
                .deploy();

        logAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logAppender.start();
        loggers.forEach(logger -> logger.addAppender(logAppender));
    }

    @AfterEach
    void tearDown() {
        loggers.forEach(logger -> logger.detachAppender(logAppender));
        logAppender.stop();
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void given_orderApprovalProcess_when_taskAssigned_then_allListenersLog() {
        // when:
        runtimeService.startProcessInstanceByKey("order-approval");

        // then: TaskAssignmentLogger (any process and the process key check),
        // TaskAssignmentVariableLogger (any process), TaskAssignmentSpelLogger (SpEL condition)
        assertThat(logMessages()).containsExactlyInAnyOrder(
                "Task 'Check order' of process 'order-approval' is assigned to alice",
                "Order approval task 'Check order' is assigned to alice",
                "Task 'Check order' for order null is assigned to alice",
                "Order approval task 'Check order' is assigned to alice (SpEL condition)");
    }

    @Test
    void given_otherProcess_when_taskAssigned_then_processKeyListenersSkip() {
        // when:
        runtimeService.startProcessInstanceByKey("other-process");

        // then: only TaskAssignmentLogger.onTaskAssigned and TaskAssignmentVariableLogger
        assertThat(logMessages()).containsExactlyInAnyOrder(
                "Task 'Check order' of process 'other-process' is assigned to alice",
                "Task 'Check order' for order null is assigned to alice");
    }

    @Test
    void given_assignedTask_when_unclaimed_then_eventWithoutUsername() {
        // given:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("other-process");
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        logAppender.list.clear();

        // when:
        taskService.unclaim(task.getId());

        // then: the event is published with a null username
        assertThat(logMessages()).containsExactlyInAnyOrder(
                "Task 'Check order' of process 'other-process' is assigned to null",
                "Task 'Check order' for order null is assigned to null");
    }

    List<String> logMessages() {
        return logAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .toList();
    }

    static String processXml(String processKey) {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                             xmlns:flowable="http://flowable.org/bpmn"
                             targetNamespace="http://www.flowable.org/processdef">
                  <process id="%s" isExecutable="true">
                    <startEvent id="start"/>
                    <sequenceFlow id="flow1" sourceRef="start" targetRef="checkOrder"/>
                    <userTask id="checkOrder" name="Check order" flowable:assignee="%s"/>
                    <sequenceFlow id="flow2" sourceRef="checkOrder" targetRef="end"/>
                    <endEvent id="end"/>
                  </process>
                </definitions>
                """.formatted(processKey, ASSIGNEE);
    }
}
