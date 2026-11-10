package com.company.bpmex1.app;

import com.company.bpmex1.test_support.AuthenticatedAsAdmin;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks the process definition examples of the Flowable API page (bpm/pages/jmix-bpm-api.adoc)
 * in ProcessDefinitionService.
 */
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
class ProcessDefinitionServiceTest {

    @Autowired
    ProcessDefinitionService processDefinitionService;

    @Autowired
    RepositoryService repositoryService;

    Deployment deployment;
    ProcessDefinition definition;

    @BeforeEach
    void setUp() {
        deployment = processDefinitionService.deployOrderProcess();
        definition = processDefinitionService.findLatestDefinition("order-process");
    }

    @AfterEach
    void tearDown() {
        if (deployment != null) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Test
    void when_deployOrderProcess_then_latestDefinitionComesFromNewDeployment() {
        assertThat(definition.getDeploymentId()).isEqualTo(deployment.getId());
    }

    @Test
    void when_suspendAndActivate_then_definitionStateChanges() {
        // when:
        processDefinitionService.suspend(definition);

        // then:
        assertThat(repositoryService.isProcessDefinitionSuspended(definition.getId())).isTrue();

        // when:
        processDefinitionService.activate(definition);

        // then:
        assertThat(repositoryService.isProcessDefinitionSuspended(definition.getId())).isFalse();
    }

    @Test
    void when_getDiagram_then_imageReturned() throws IOException {
        try (InputStream diagram = processDefinitionService.getDiagram(definition)) {
            assertThat(diagram).isNotNull();
            assertThat(diagram.readAllBytes()).isNotEmpty();
        }
    }
}
