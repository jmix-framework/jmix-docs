package com.company.onboarding.view.backgroundtasks;

import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import io.jmix.flowui.Dialogs;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.backgroundtask.BackgroundTask;
import io.jmix.flowui.backgroundtask.TaskLifeCycle;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Route(value = "BackgroundTasksView", layout = MainView.class)
@ViewController("BackgroundTasksView")
@ViewDescriptor("background-tasks-view.xml")
public class BackgroundTasksView extends StandardListView {

    //tag::background-tasks-email[]
    @ViewComponent
    private DataGrid<User> usersTable;

    @Autowired
    private Emailer emailer;

    @Autowired
    private Dialogs dialogs;

    @Subscribe("sendByEmail")
    public void onSendByEmailClick(ClickEvent event) {
        Set<User> selected = usersTable.getSelectedItems();
        if (selected.isEmpty()) {
            return;
        }
        BackgroundTask<Integer, Void> task = new EmailTask(selected);
        dialogs.createBackgroundTaskDialog(task) // <1>
                .withHeader("Sending reminder emails")
                .withText("Please wait while emails are being sent")
                .withTotal(selected.size())
                .withShowProgressInPercentage(true)
                .withCancelAllowed(true)
                .open();
    }

    private class EmailTask extends BackgroundTask<Integer, Void> { // <2>

        private Set<User> users; // <3>

        public EmailTask(Set<User> users) {
            super(10, TimeUnit.MINUTES, BackgroundTasksView.this); // <4>
            this.users = users;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            int i = 0;
            for (User user : users) {
                if (taskLifeCycle.isCancelled()) { // <5>
                    break;
                }
                EmailInfo emailInfo = EmailInfoBuilder.create() // <6>
                        .setAddresses(user.getEmail())
                        .setSubject("Reminder")
                        .setBody("Your password expires in 14 days!")
                        .build();
                emailer.sendEmail(emailInfo);
                i++;
                taskLifeCycle.publish(i); // <7>
            }
            return null;
        }
    }
    //end::background-tasks-email[]
}