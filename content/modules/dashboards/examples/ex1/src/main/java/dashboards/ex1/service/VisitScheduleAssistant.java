package dashboards.ex1.service;

import dashboards.ex1.screen.widgets.VisitsCalendarWidget;
import io.jmix.dashboardsui.component.Dashboard;
import io.jmix.dashboardsui.dashboard.assistant.DashboardViewAssistant;
import io.jmix.dashboardsui.event.DashboardUpdatedEvent;
import io.jmix.ui.screen.ScreenFragment;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// tag::business-logic[]
@Component("sample_VisitScheduleAssistant")
@Scope("prototype") // <1>
public class VisitScheduleAssistant implements DashboardViewAssistant { //<2>

    protected Dashboard dashboard;

    @Override
    public void init(Dashboard dashboard) { // <3>
        this.dashboard = dashboard;
    }

    @EventListener
    public void dashboardEventListener(DashboardUpdatedEvent event){ // <4>
        ScreenFragment widget = dashboard.getWidget("visits-calendar"); // <5>
        if (widget instanceof VisitsCalendarWidget){ // <6>
            VisitsCalendarWidget visitsCalendarWidget = (VisitsCalendarWidget) widget;
            visitsCalendarWidget.reloadSchedule();
        }
    }

}
// end::business-logic[]