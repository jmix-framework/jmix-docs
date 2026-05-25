package bpm.ex1.service;

public interface FailedJobDemoService {
    String NAME = "smpl_FailedJobDemoService";

    void methodThatFails(boolean fail);
}
