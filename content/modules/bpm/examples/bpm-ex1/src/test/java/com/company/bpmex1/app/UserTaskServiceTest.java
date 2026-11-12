package com.company.bpmex1.app;

import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import org.flowable.common.engine.api.FlowableTaskAlreadyClaimedException;
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

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Checks the TaskService examples of the User Task page (bpm/pages/bpmn/bpmn-user-task.adoc)
 * in UserTaskService.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class UserTaskServiceTest {

    @Autowired
    UserTaskService userTaskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    Deployment deployment;
    Task task;

    @BeforeEach
    void setUp() {
        deployment = repositoryService.createDeployment()
                .name("user-task-service-test")
                .addString("user-task-service-test.bpmn20.xml", PROCESS_XML)
                .deploy();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("user-task-service-test");
        task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
    }

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void given_taskWithDueDate_when_postponeDueDate_then_dueDateMovedByDelay() {
        // given:
        Instant dueDate = Instant.parse("2026-12-01T10:00:00Z");
        taskService.setDueDate(task.getId(), Date.from(dueDate));

        // when:
        userTaskService.postponeDueDate(task.getId(), Duration.ofDays(2));

        // then:
        assertThat(reloadTask().getDueDate().getTime())
                .isEqualTo(dueDate.plus(Duration.ofDays(2)).toEpochMilli());
    }

    @Test
    void given_taskWithoutDueDate_when_postponeDueDate_then_noDueDate() {
        // when:
        userTaskService.postponeDueDate(task.getId(), Duration.ofDays(2));

        // then:
        assertThat(reloadTask().getDueDate()).isNull();
    }

    @Test
    void when_assign_then_taskInAssignedTasksOfUser() {
        // given:
        String username = "user-task-test-" + UUID.randomUUID();

        // when:
        userTaskService.assign(task.getId(), username);

        // then:
        assertThat(userTaskService.getAssignedTasks(username))
                .extracting(Task::getId)
                .containsExactly(task.getId());
    }

    @Test
    void given_unassignedTask_when_claim_then_currentUserIsAssignee() {
        // when:
        userTaskService.claim(task.getId());

        // then:
        assertThat(reloadTask().getAssignee()).isEqualTo("admin");
    }

    @Test
    void given_taskClaimedByAnotherUser_when_claim_then_exception() {
        // given:
        taskService.claim(task.getId(), "alice");

        // when, then:
        assertThatThrownBy(() -> userTaskService.claim(task.getId()))
                .isInstanceOf(FlowableTaskAlreadyClaimedException.class);
        assertThat(reloadTask().getAssignee()).isEqualTo("alice");
    }

    Task reloadTask() {
        return taskService.createTaskQuery()
                .taskId(task.getId())
                .singleResult();
    }

    static final String PROCESS_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         xmlns:flowable="http://flowable.org/bpmn"
                         targetNamespace="http://www.flowable.org/processdef">
              <process id="user-task-service-test" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="reviewOrder"/>
                <userTask id="reviewOrder" name="Review order"/>
                <sequenceFlow id="flow2" sourceRef="reviewOrder" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;
}
