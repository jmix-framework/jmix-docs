package bpm.ex1.service;

import org.flowable.task.api.Task;

// tag::custom[]
public interface AcceptsTask {
    void setTask(Task task);
}
// end::custom[]
