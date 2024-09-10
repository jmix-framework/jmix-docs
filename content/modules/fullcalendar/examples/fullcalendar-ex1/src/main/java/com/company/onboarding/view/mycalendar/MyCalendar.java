package com.company.onboarding.view.mycalendar;


import com.company.onboarding.entity.Meeting;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "my-calendar", layout = MainView.class)
@ViewController("MyCalendar")
@ViewDescriptor("my-calendar.xml")
public class MyCalendar extends StandardView {
    @ViewComponent
    private CollectionLoader<Meeting> meetingsDl;
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        final User user = (User) currentAuthentication.getUser();
        meetingsDl.setParameter("user", user);
        meetingsDl.load();
    }
}