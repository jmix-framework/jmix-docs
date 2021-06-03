package bpm.ex1.service;

import org.flowable.engine.repository.ProcessDefinition;

// tag::custom[]
public interface AcceptsProcessDefinition {
    void setProcessDefinition(ProcessDefinition processDefinition);
}
// end::custom[]
