package com.company.onboarding.exception;

import io.jmix.flowui.exception.ExceptionDialog;
import io.jmix.flowui.exception.ExceptionDialogProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class MyExceptionDialogProvider implements ExceptionDialogProvider {

    private final ObjectProvider<MyExceptionDialog> myExceptionDialogProvider; // <1>

    public MyExceptionDialogProvider(ObjectProvider<MyExceptionDialog> myExceptionDialogProvider) {
        this.myExceptionDialogProvider = myExceptionDialogProvider;
    }

    @Override
    public boolean supports(Throwable throwable) {
        return true; // <2>
    }

    @Override
    public ExceptionDialog getExceptionDialogOpener(Throwable throwable) {
        return myExceptionDialogProvider.getObject(throwable); // <3>
    }
}
