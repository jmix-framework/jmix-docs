package com.company.onboarding.view.timer;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "TimerView", layout = MainView.class)
@ViewController("TimerView")
@ViewDescriptor("timer-view.xml")
public class TimerView extends StandardView {
    @ViewComponent
    private Timer timer;

    @Subscribe
    public void onInit(final InitEvent event) {
    }

    // tag::timer-action[]
    @Autowired
    private Notifications notifications;

    @Subscribe("timer")
    public void onTimerTimerAction(final Timer.TimerActionEvent event) {
        notifications.show("Timer action");
    }
    // end::timer-action[]

    @Subscribe("timer")
    public void onTimerTimerStop(final Timer.TimerStopEvent event) {
    }
}