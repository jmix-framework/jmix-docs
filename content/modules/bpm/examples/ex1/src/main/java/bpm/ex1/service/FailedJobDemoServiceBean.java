package bpm.ex1.service;

import org.springframework.stereotype.Component;

@Component("smpl_FailedJobDemoBean")
public class FailedJobDemoServiceBean implements FailedJobDemoService{

    @Override
    public void methodThatFails(boolean fail) {
        if (fail) throw new RuntimeException("Method failed!");
    }
}