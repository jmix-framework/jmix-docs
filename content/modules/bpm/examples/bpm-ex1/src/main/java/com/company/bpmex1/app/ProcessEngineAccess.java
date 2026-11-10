package com.company.bpmex1.app;

import io.jmix.bpm.service.BpmTaskService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;

/**
 * Gets the Flowable services in code that isn't a Spring bean.
 */
public final class ProcessEngineAccess {

    public record Services(RuntimeService runtimeService,
                           RepositoryService repositoryService,
                           BpmTaskService bpmTaskService) {
    }

    private ProcessEngineAccess() {
    }

    public static Services getServices() {
        // tag::services[]
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        BpmTaskService bpmTaskService = (BpmTaskService) processEngine.getTaskService();
        // end::services[]
        return new Services(runtimeService, repositoryService, bpmTaskService);
    }
}
