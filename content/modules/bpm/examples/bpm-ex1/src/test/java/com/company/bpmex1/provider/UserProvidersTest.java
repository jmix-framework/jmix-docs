package com.company.bpmex1.provider;

import com.company.bpmex1.entity.User;
import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import io.jmix.bpm.entity.UserGroup;
import io.jmix.bpm.entity.UserGroupType;
import io.jmix.core.DataManager;
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

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the user, user list and user group list providers of the User Task page
 * (bpm/pages/bpmn/bpmn-user-task.adoc), and the user task XML with a user provider shown on the page.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class UserProvidersTest {

    @Autowired
    UserByEmailProvider userByEmailProvider;

    @Autowired
    ActiveUsersProvider activeUsersProvider;

    @Autowired
    AllUserGroupsProvider allUserGroupsProvider;

    @Autowired
    DataManager dataManager;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    User user;
    UserGroup userGroup;
    Deployment deployment;

    @BeforeEach
    void setUp() {
        String suffix = UUID.randomUUID().toString();

        User newUser = dataManager.create(User.class);
        newUser.setUsername("provider-test-" + suffix);
        newUser.setEmail("provider-test-" + suffix + "@company.com");
        newUser.setActive(true);
        user = dataManager.save(newUser);

        UserGroup newUserGroup = dataManager.create(UserGroup.class);
        newUserGroup.setCode("provider-test-" + suffix);
        newUserGroup.setName("Provider test group");
        newUserGroup.setType(UserGroupType.USERS);
        userGroup = dataManager.save(newUserGroup);
    }

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
        dataManager.remove(user);
        dataManager.remove(userGroup);
    }

    @Test
    void given_userWithEmail_when_getUserByEmail_then_usernameReturned() {
        assertThat(userByEmailProvider.getUserByEmail(user.getEmail()))
                .isEqualTo(user.getUsername());
    }

    @Test
    void given_userTaskWithUserProvider_when_processStarted_then_taskAssignedToUserWithEmail() {
        // given:
        deployment = repositoryService.createDeployment()
                .name("user-provider-test")
                .addString("user-provider-test.bpmn20.xml", USER_PROVIDER_PROCESS_XML)
                .deploy();

        // when:
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                "user-provider-test", Map.of("customerEmail", user.getEmail()));

        // then:
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
        assertThat(task.getAssignee()).isEqualTo(user.getUsername());
    }

    @Test
    void given_activeUser_when_getActiveUsers_then_usernameReturned() {
        assertThat(activeUsersProvider.getActiveUsers())
                .contains(user.getUsername(), "admin");
    }

    @Test
    void given_userGroup_when_getAllUserGroups_then_codeReturned() {
        assertThat(allUserGroupsProvider.getAllUserGroups())
                .contains(userGroup.getCode());
    }

    // The user task is the XML example of the User Task page
    static final String USER_PROVIDER_PROCESS_XML = """
            <?xml version="1.0" encoding="UTF-8"?>
            <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                         xmlns:flowable="http://flowable.org/bpmn"
                         xmlns:jmix="http://jmix.io/schema/bpm/bpmn"
                         targetNamespace="http://www.flowable.org/processdef">
              <process id="user-provider-test" isExecutable="true">
                <startEvent id="start"/>
                <sequenceFlow id="flow1" sourceRef="start" targetRef="user-task"/>
                <userTask id="user-task" name="User task"
                    flowable:assignee="${UserByEmailProvider.getUserByEmail(customerEmail)}"
                    jmix:assigneeSource="userProvider"
                    jmix:assigneeValue="UserByEmailProvider">
                  <extensionElements>
                    <jmix:springBean beanName="UserByEmailProvider" methodName="getUserByEmail">
                      <jmix:methodParam name="email" type="java.lang.String"
                          isVariable="true">customerEmail</jmix:methodParam>
                    </jmix:springBean>
                  </extensionElements>
                </userTask>
                <sequenceFlow id="flow2" sourceRef="user-task" targetRef="end"/>
                <endEvent id="end"/>
              </process>
            </definitions>
            """;
}
