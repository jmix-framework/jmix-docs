package ui.ex1.screen.facets.timer;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.Timer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_TimerScreen")
@UiDescriptor("timer-screen.xml")
public class TimerScreen extends Screen {
    // tag::timer[]
    @Autowired
    protected Timer timer;
    @Autowired
    protected Label<String> statusLabel;
    @Autowired
    protected Notifications notifications;

    protected int seconds = 0;

    @Subscribe("startTimer")
    protected void onStartTimerClick(Button.ClickEvent event) {
        timer.start(); // <1>
        statusLabel.setValue("Timer started");
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer started")
                .show();
    }

    // tag::timer-action-event[]
    @Subscribe("timer")
    protected void onTimerTick(Timer.TimerActionEvent event) {
        seconds += event.getSource().getDelay() / 1000; // <2>
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer tick")
                .withDescription(seconds + " seconds passed")
                .show();
    }
    // end::timer-action-event[]

    @Subscribe("stopTimer")
    protected void onStopTimerClick(Button.ClickEvent event) {
        timer.stop(); // <3>
        seconds = 0;
        statusLabel.setValue("Timer stopped");
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer stopped")
                .show();
    }
    // end::timer[]

    // tag::timer-stop-event[]
    @Subscribe("timer")
    public void onTimerTimerStop(Timer.TimerStopEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer stopped")
                .show();
    }
    // end::timer-stop-event[]

    // tag::timer-action-event-1[]
    @Subscribe("timer")
    protected void onTimerFacetTick(Timer.TimerActionEvent event) {
        seconds += event.getSource().getDelay() / 1000;
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Timer tick")
                .withDescription(seconds + " seconds passed")
                .show();
    }
    // end::timer-action-event-1[]
}
