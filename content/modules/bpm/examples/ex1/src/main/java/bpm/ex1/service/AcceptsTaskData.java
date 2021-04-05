package bpm.ex1.service;

import io.jmix.bpm.entity.TaskData;
import org.flowable.engine.form.TaskFormData;

// tag::custom[]
public interface AcceptsTaskData {
    void setTaskData(TaskData taskData);
}
// end::custom[]
