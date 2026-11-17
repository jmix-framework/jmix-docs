package com.company.bpmex1.app;

import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import io.jmix.bpm.service.BpmTaskService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the order approval process that the demo application starts from the order detail view
 * (processes/order-approval.bpmn20.xml) together with OrderApprovalService.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class OrderApprovalProcessTest {

    @Autowired
    OrderApprovalService orderApprovalService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    BpmTaskService bpmTaskService;

    Deployment deployment;
    ProcessDefinition definition;

    @BeforeEach
    void setUp() {
        deployment = repositoryService.createDeployment()
                .name("order-approval-process-test")
                .addClasspathResource("processes/order-approval.bpmn20.xml")
                .deploy();
        definition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
    }

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void given_startedByUser_when_getMyApprovalTasks_then_checkOrderTaskOfInitiator() {
        // given:
        ProcessInstance processInstance = startApproval();

        // when:
        Task task = currentTask(processInstance);

        // then:
        assertThat(definition.getKey()).isEqualTo("order-approval");
        assertThat(task.getTaskDefinitionKey()).isEqualTo("checkOrder");
        assertThat(task.getAssignee()).isEqualTo("admin");
        assertThat(orderApprovalService.getMyApprovalTasks())
                .extracting(Task::getId)
                .contains(task.getId());
    }

    @Test
    void given_checkOrderTask_when_approved_then_shipOrderAndProcessEnds() {
        // given:
        ProcessInstance processInstance = startApproval();

        // when:
        orderApprovalService.approve(currentTask(processInstance), "Looks good");

        // then:
        Task shipTask = currentTask(processInstance);
        assertThat(shipTask.getTaskDefinitionKey()).isEqualTo("shipOrder");
        assertThat(shipTask.getAssignee()).isEqualTo("admin");

        taskService.complete(shipTask.getId());
        assertThat(isActive(processInstance)).isFalse();
    }

    @Test
    void given_checkOrderTask_when_rejected_then_processEnds() {
        // given:
        ProcessInstance processInstance = startApproval();

        // when:
        bpmTaskService.completeTaskWithOutcome(currentTask(processInstance).getId(), "reject", new HashMap<>());

        // then:
        assertThat(isActive(processInstance)).isFalse();
    }

    ProcessInstance startApproval() {
        return runtimeService.startProcessInstanceById(definition.getId(), "process-test-" + UUID.randomUUID());
    }

    Task currentTask(ProcessInstance processInstance) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
    }

    boolean isActive(ProcessInstance processInstance) {
        return runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .count() > 0;
    }
}
