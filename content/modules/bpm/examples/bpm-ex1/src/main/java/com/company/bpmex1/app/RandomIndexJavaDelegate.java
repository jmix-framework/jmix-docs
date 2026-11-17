package com.company.bpmex1.app;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.concurrent.ThreadLocalRandom;

// tag::delegate[]
public class RandomIndexJavaDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        long randomIndex = ThreadLocalRandom.current().nextLong(100L);
        execution.setVariable("randomIndex", randomIndex);
    }
}
// end::delegate[]
