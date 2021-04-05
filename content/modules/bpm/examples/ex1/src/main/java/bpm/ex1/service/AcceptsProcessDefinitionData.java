package bpm.ex1.service;

import io.jmix.bpm.entity.ProcessDefinitionData;
// tag::custom[]
public interface AcceptsProcessDefinitionData {
    void setProcessDefinitionData(ProcessDefinitionData processDefinitionData);
}
// end::custom[]
