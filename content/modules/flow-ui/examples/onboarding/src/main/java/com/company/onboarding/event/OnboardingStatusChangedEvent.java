package com.company.onboarding.event;

//tag::application-event[]
import org.springframework.context.ApplicationEvent;

public class OnboardingStatusChangedEvent extends ApplicationEvent {

    public OnboardingStatusChangedEvent(Object source) {
        super(source);
    }
}
//end::application-event[]
