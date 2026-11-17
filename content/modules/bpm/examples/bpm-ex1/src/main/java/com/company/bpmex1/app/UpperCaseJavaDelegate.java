package com.company.bpmex1.app;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tag::delegate[]
public class UpperCaseJavaDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(UpperCaseJavaDelegate.class);

    // tag::fields[]
    private Expression messageField;
    private Expression quantityField;
    // end::fields[]

    @Override
    public void execute(DelegateExecution execution) {
        // tag::get-values[]
        String message = (String) messageField.getValue(execution);
        int quantity = ((Number) quantityField.getValue(execution)).intValue(); // <1>
        // end::get-values[]

        String upperCaseMessage = message.toUpperCase();
        for (int i = 0; i < quantity; i++) {
            log.info(upperCaseMessage);
        }
    }
}
// end::delegate[]
