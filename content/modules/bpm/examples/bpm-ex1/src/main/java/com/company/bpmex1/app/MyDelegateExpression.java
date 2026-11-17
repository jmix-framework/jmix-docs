package com.company.bpmex1.app;

import io.jmix.core.TimeSource;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// tag::delegate[]
@Component("MyDelegateExpression") // <1>
public class MyDelegateExpression implements JavaDelegate {

    @Autowired
    private TimeSource timeSource; // <2>

    @Override
    public void execute(DelegateExecution execution) {
        execution.setVariable("checkedAt", timeSource.currentTimestamp()); // <3>
    }
}
// end::delegate[]
