package com.company.bpmex1.app;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class ProcessDefinitionService {

    @Autowired
    private RepositoryService repositoryService;

    // tag::deploy[]
    public Deployment deployOrderProcess() {
        return repositoryService.createDeployment()
                .addClasspathResource("processes/order-process.bpmn20.xml")
                .deploy();
    }
    // end::deploy[]

    // tag::find-definition[]
    public ProcessDefinition findLatestDefinition(String processDefinitionKey) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
    }
    // end::find-definition[]

    // tag::suspend[]
    public void suspend(ProcessDefinition processDefinition) {
        repositoryService.suspendProcessDefinitionById(processDefinition.getId());
    }

    public void activate(ProcessDefinition processDefinition) {
        repositoryService.activateProcessDefinitionById(processDefinition.getId());
    }
    // end::suspend[]

    // tag::diagram[]
    public InputStream getDiagram(ProcessDefinition processDefinition) {
        return repositoryService.getProcessDiagram(processDefinition.getId());
    }
    // end::diagram[]
}
