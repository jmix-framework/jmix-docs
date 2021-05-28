package bpm.ex1.listener;

import bpm.ex1.entity.User;
import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import io.jmix.core.DataManager;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

// tag::event-listener-1[]
@Component("smpl_TaskAssignedNotificationSender")
public class TaskAssignedNotificationSender {

    @Autowired
    private Emailer emailer;

    @Autowired
    private DataManager dataManager;

    @EventListener // <1>
    public void onTaskAssigned(UserTaskAssignedEvent event) { // <2>
        User user = dataManager.load(User.class) // <3>
                .query("select u from smpl_User u where u.username = :username")
                .parameter("username", event.getUsername())
                .one();
        Task task = event.getTask(); // <4>
        String emailTitle = "New process task " + task.getName();
        String emailBody = "Hi " + user.getFirstName() + "\n" +
                "The task " + task.getName() + " has been assigned.";
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(user.getEmail())
                .setSubject(emailTitle)
                .setFrom(null)
                .setBody(emailBody)
                .build();
        emailer.sendEmailAsync(emailInfo); // <5>
    }
    // end::event-listener-1[]

    // tag::specific-process-1[]
    @EventListener
    protected void onOtherProcessTaskAssigned(UserTaskAssignedEvent event) {
        if ("order-approval".equals(event.getProcessDefinition().getKey())) {
            // ...
            // end::specific-process-1[]
            User user = dataManager.load(User.class)
                    .query("select u from smpl_User u where u.username = "+":username")
                    .parameter("username", event.getUsername())
                    .one();
            Task task = event.getTask();
            String emailTitle = "New process task " + task.getName();
            String emailBody = "Hi " + user.getFirstName() + "\n" +
                    "The task" + task.getName() + "has been assigned.";
            EmailInfo emailInfo = EmailInfoBuilder.create()
                    .setAddresses(user.getEmail())
                    .setSubject(emailTitle)
                    .setFrom(null)
                    .setBody(emailBody)
                    .build();
            emailer.sendEmailAsync(emailInfo);
            // tag::specific-process-2[]
        }
    }
    // end::specific-process-2[]
    // tag::event-listener-2[]
}
// end::event-listener-2[]