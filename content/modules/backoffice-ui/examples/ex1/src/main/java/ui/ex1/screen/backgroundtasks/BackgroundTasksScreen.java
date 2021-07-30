package ui.ex1.screen.backgroundtasks;

import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import io.jmix.ui.Dialogs;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@UiController("sample_BackgroundTasksScreen")
@UiDescriptor("background-tasks-screen.xml")
public class BackgroundTasksScreen extends Screen {
    // tag::background-tasks-email[]
    @Autowired
    private Table<Customer> customersTable;
    @Autowired
    private Emailer emailer;
    @Autowired
    private Dialogs dialogs;

    @Subscribe("sendByEmail")
    public void onSendByEmailClick(Button.ClickEvent event) {
        Set<Customer> selected = customersTable.getSelected();
        if (selected.isEmpty()) {
            return;
        }
        BackgroundTask<Integer, Void> task = new EmailTask(selected);
        dialogs.createBackgroundWorkDialog(this, task) // <1>
                .withCaption("Sending reminder emails")
                .withMessage("Please wait while emails are being sent")
                .withTotal(selected.size())
                .withShowProgressInPercentage(true)
                .withCancelAllowed(true)
                .show();
    }

    private class EmailTask extends BackgroundTask<Integer, Void> { // <2>
        private Set<Customer> customers; // <3>

        public EmailTask(Set<Customer> customers) {
            super(10, TimeUnit.MINUTES, BackgroundTasksScreen.this); // <4>
            this.customers = customers;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            int i = 0;
            for (Customer customer : customers) {
                if (taskLifeCycle.isCancelled()) { // <5>
                    break;
                }
                EmailInfo emailInfo = EmailInfoBuilder.create() // <6>
                        .setAddresses(customer.getEmail())
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
    // end::background-tasks-email[]
}