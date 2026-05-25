package com.company.onboarding.exception;

import io.jmix.flowui.Notifications;
import io.jmix.flowui.exception.AbstractUiExceptionHandler;
import org.springframework.stereotype.Component;

@Component // <1>
public class MyExceptionHandler extends AbstractUiExceptionHandler { // <2>

    private final Notifications notifications; // <3>

    public MyExceptionHandler(Notifications notifications) {
        super(MyException.class.getName()); // <4>
        this.notifications = notifications;
    }

    @Override
    protected void doHandle(String className, String message, Throwable throwable) {
        notifications.show("My exception", throwable.getMessage()); // <5>
    }
}
