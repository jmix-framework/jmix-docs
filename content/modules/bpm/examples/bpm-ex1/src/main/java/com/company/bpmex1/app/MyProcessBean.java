package com.company.bpmex1.app;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

// tag::bean[]
@Component("MyProcessBean")
public class MyProcessBean {

    private static final Logger log = LoggerFactory.getLogger(MyProcessBean.class);

    public void mySampleMethod(DelegateExecution execution) { // <1>
        String currentActivityId = execution.getCurrentActivityId(); // <2>
        Set<String> variableNames = execution.getVariableNames();
        log.info("Process {} is at {} with variables {}",
                execution.getProcessInstanceBusinessKey(), currentActivityId, variableNames);
    }
}
// end::bean[]
