package com.company.bpmex1.app;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the examples of the Service Task page (bpm/pages/bpmn/bpmn-service-task.adoc):
 * a Spring bean method with the execution parameter, a Java delegate class, a delegate expression
 * and field injection. Each process stops at a user task after the service task, so the test can read
 * the variables that the service task sets.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class ServiceTaskExamplesTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    Deployment deployment;

    final ListAppender<ILoggingEvent> logAppender = new ListAppender<>();

    final List<Logger> loggers = List.of(
            (Logger) LoggerFactory.getLogger(MyProcessBean.class),
            (Logger) LoggerFactory.getLogger(UpperCaseJavaDelegate.class));

    @BeforeEach
    void setUp() {
        deployment = repositoryService.createDeployment()
                .name("service-task-examples-test")
                .addString("spring-bean-execution.bpmn20.xml", process("spring-bean-execution",
                        "flowable:expression=\"${MyProcessBean.mySampleMethod(execution)}\"", ""))
                .addString("java-delegate.bpmn20.xml", process("java-delegate",
                        "flowable:class=\"com.company.bpmex1.app.RandomIndexJavaDelegate\"", ""))
                .addString("delegate-expression.bpmn20.xml", process("delegate-expression",
                        "flowable:delegateExpression=\"${MyDelegateExpression}\"", ""))
                .addString("field-injection.bpmn20.xml", process("field-injection",
                        "flowable:class=\"com.company.bpmex1.app.UpperCaseJavaDelegate\"",
                        """
                        <extensionElements>
                          <flowable:field name="messageField">
                            <flowable:string><![CDATA[hello]]></flowable:string>
                          </flowable:field>
                          <flowable:field name="quantityField">
                            <flowable:expression><![CDATA[${3}]]></flowable:expression>
                          </flowable:field>
                        </extensionElements>
                        """))
                .deploy();

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
    void given_executionParameter_when_beanMethodCalled_then_methodSeesProcessContext() {
        // when:
        runtimeService.startProcessInstanceByKey("spring-bean-execution", "service-task-test",
                Map.of("orderNumber", "42"));

        // then: the variables also include initiator, which Jmix BPM sets on start
        assertThat(logMessages()).singleElement().asString()
                .startsWith("Process service-task-test is at serviceTask with variables [")
                .contains("orderNumber");
    }

    @Test
    void given_javaDelegateClass_when_taskExecuted_then_randomIndexSet() {
        // when:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("java-delegate");

        // then:
        Object randomIndex = runtimeService.getVariable(processInstance.getId(), "randomIndex");
        assertThat(randomIndex).isInstanceOf(Long.class);
        assertThat((Long) randomIndex).isBetween(0L, 99L);
    }

    @Test
    void given_delegateExpression_when_taskExecuted_then_springBeanDelegateUsed() {
        // when:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("delegate-expression");

        // then: the delegate is the Spring bean, so its injected TimeSource works
        assertThat(runtimeService.getVariable(processInstance.getId(), "checkedAt"))
                .isInstanceOf(Date.class);
    }

    @Test
    void given_fieldInjection_when_taskExecuted_then_fieldValuesUsed() {
        // when:
        runtimeService.startProcessInstanceByKey("field-injection");

        // then: the string field gives a String, the ${3} expression gives a number
        assertThat(logMessages()).containsExactly("HELLO", "HELLO", "HELLO");
    }

    List<String> logMessages() {
        return logAppender.list.stream()
                .map(ILoggingEvent::getFormattedMessage)
                .toList();
    }

    static String process(String key, String serviceTaskAttributes, String serviceTaskBody) {
        return """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                             xmlns:flowable="http://flowable.org/bpmn"
                             targetNamespace="http://www.flowable.org/processdef">
                  <process id="%s" isExecutable="true">
                    <startEvent id="start"/>
                    <sequenceFlow id="flow1" sourceRef="start" targetRef="serviceTask"/>
                    <serviceTask id="serviceTask" %s>
                    %s
                    </serviceTask>
                    <sequenceFlow id="flow2" sourceRef="serviceTask" targetRef="wait"/>
                    <userTask id="wait"/>
                    <sequenceFlow id="flow3" sourceRef="wait" targetRef="end"/>
                    <endEvent id="end"/>
                  </process>
                </definitions>
                """.formatted(key, serviceTaskAttributes, serviceTaskBody);
    }
}
