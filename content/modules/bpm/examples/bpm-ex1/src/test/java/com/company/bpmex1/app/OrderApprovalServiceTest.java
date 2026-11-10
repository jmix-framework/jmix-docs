package com.company.bpmex1.app;

import com.company.bpmex1.entity.Order;
import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import io.jmix.bpm.data.outcome.Outcome;
import io.jmix.bpm.data.outcome.OutcomesContainer;
import io.jmix.core.Metadata;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the examples of the Flowable API page (bpm/pages/jmix-bpm-api.adoc)
 * in OrderApprovalService and ProcessEngineAccess.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class OrderApprovalServiceTest {

    @Autowired
    OrderApprovalService orderApprovalService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    Metadata metadata;

    Deployment deployment;
    Order order;

    @BeforeEach
    void setUp() {
        deployment = repositoryService.createDeployment()
                .name("order-approval-service-test")
                .addString("order-approval.bpmn20.xml", PROCESS_XML)
                .deploy();

        order = metadata.create(Order.class);
        order.setNumber("api-test-" + UUID.randomUUID());
    }

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void given_taskAssignedToCurrentUser_when_getMyApprovalTasks_then_taskReturned() {
        // given:
        ProcessInstance processInstance = startApproval();

        // when:
        List<Task> tasks = orderApprovalService.getMyApprovalTasks();

        // then:
        assertThat(tasks)
                .extracting(Task::getProcessInstanceId)
                .contains(processInstance.getId());
        assertThat(tasks)
                .extracting(Task::getAssignee)
                .containsOnly("admin");
    }

    @Test
    void given_approvalStarted_when_getActiveApprovals_then_instanceFoundByOrderNumber() {
        // given:
        ProcessInstance processInstance = startApproval();

        // when:
        List<ProcessInstance> approvals = orderApprovalService.getActiveApprovals(order);

        // then:
        assertThat(approvals)
                .extracting(ProcessInstance::getId)
                .containsExactly(processInstance.getId());
    }

    @Test
    void given_checkOrderTask_when_approve_then_taskCompletedWithOutcome() {
        // given:
        ProcessInstance processInstance = startApproval();
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();

        // when:
        orderApprovalService.approve(task, "Looks good");

        // then: the process moves on, the outcome and the comment are process variables
        Task nextTask = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        assertThat(nextTask.getTaskDefinitionKey()).isEqualTo("shipOrder");

        OutcomesContainer outcomes = (OutcomesContainer) runtimeService.getVariable(
                processInstance.getId(), "checkOrder_result");
        assertThat(outcomes.getOutcomes())
                .extracting(Outcome::getOutcomeId)
                .containsExactly("approve");
        assertThat(runtimeService.getVariable(processInstance.getId(), "comment"))
                .isEqualTo("Looks good");
    }

    @Test
    void given_orderNumberChanged_when_updateBusinessKey_then_instanceFoundByNewNumber() {
        // given:
        ProcessInstance processInstance = startApproval();
        order.setNumber(order.getNumber() + "-changed");

        // when:
        orderApprovalService.updateBusinessKey(processInstance, order);

        // then:
        assertThat(orderApprovalService.getActiveApprovals(order))
                .extracting(ProcessInstance::getId)
                .containsExactly(processInstance.getId());
    }

    @Test
    void given_approvalStarted_when_servicesFromProcessEngine_then_servicesWork() {
        // given:
        ProcessInstance processInstance = startApproval();

        // when:
        ProcessEngineAccess.Services services = ProcessEngineAccess.getServices();

        // then: the cast to BpmTaskService succeeded and the services see the process
        assertThat(services.runtimeService().createProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .count())
                .isEqualTo(1);
        assertThat(services.repositoryService().createDeploymentQuery()
                .deploymentId(deployment.getId())
                .count())
                .isEqualTo(1);
        assertThat(services.bpmTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId())
                .count())
                .isEqualTo(1);
    }

    ProcessInstance startApproval() {
        return runtimeService.startProcessInstanceByKey("order-approval", order.getNumber());
    }

    static final String PROCESS_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         xmlns:flowable="http://flowable.org/bpmn"
                         targetNamespace="http://www.flowable.org/processdef">
              <process id="order-approval" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="checkOrder"/>
                <userTask id="checkOrder" name="Check order" flowable:assignee="admin"/>
                <sequenceFlow id="flow2" sourceRef="checkOrder" targetRef="shipOrder"/>
                <userTask id="shipOrder" name="Ship order"/>
                <sequenceFlow id="flow3" sourceRef="shipOrder" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;
}
