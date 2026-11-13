package com.company.bpmex1;

import com.company.bpmex1.entity.User;
import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import io.jmix.bpm.config.BpmProperties;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the BPM application properties shown on the User Task page (bpm/pages/bpmn/bpmn-user-task.adoc)
 * and the Entity Data Task page (bpm/pages/bpmn/jmix-entity-data-task.adoc).
 * The properties are in application-custom-bpm-properties.properties.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles({"test", "custom-bpm-properties"})
class BpmPropertiesTest {

    @Autowired
    BpmProperties bpmProperties;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    Deployment deployment;

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void given_initiatorVariableName_when_userStartsProcess_then_initiatorInThatVariable() {
        // given:
        deployment = repositoryService.createDeployment()
                .name("initiator-variable-test")
                .addString("initiator-variable-test.bpmn20.xml", PROCESS_XML)
                .deploy();

        // when:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("initiator-variable-test");

        // then: the process started from code by an authenticated user has the initiator variable
        Object initiator = runtimeService.getVariable(processInstance.getId(), "processInitiator");
        assertThat(initiator).isInstanceOf(User.class);
        assertThat(((User) initiator).getUsername()).isEqualTo("admin");
        assertThat(runtimeService.hasVariable(processInstance.getId(), "initiator")).isFalse();
    }

    @Test
    void when_springBeanEntityAttributeProperty_then_enabled() {
        assertThat(bpmProperties.isDataTaskSpringBeanEntityAttributeEnabled()).isTrue();
    }

    static final String PROCESS_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         targetNamespace="http://www.flowable.org/processdef">
              <process id="initiator-variable-test" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="checkOrder"/>
                <userTask id="checkOrder" name="Check order"/>
                <sequenceFlow id="flow2" sourceRef="checkOrder" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;
}
