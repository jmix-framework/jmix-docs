package bpm.ex1.listener;

import bpm.ex1.entity.User;
import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import io.jmix.core.DataManager;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("smpl_TaskAssignedNotificationSender3")
public class TaskAssignedNotificationSender3 {
    @Autowired
    private Emailer emailer;

    @Autowired
    private DataManager dataManager;

    // tag::spel-example-1[]
    @EventListener(condition = "#event.processDefinitionData.key == 'order-approval'")
    protected void onOtherProcessTaskAssigned(UserTaskAssignedEvent event) {
        // ...
        // end::spel-example-1[]
        User user = dataManager.load(User.class)
                .query("select u from smpl_User u where u.username = :username")
                .parameter("username", event.getUsername())
                .one();
        Task task = event.getTask();
        String emailTitle = "New process task " + task.getName();
        String emailBody = "Hi " + user.getFirstName() + "\n" +
                "The task " + task.getName() + " has been assigned.";
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(user.getEmail())
                .setSubject(emailTitle)
                .setFrom(null)
                .setBody(emailBody)
                .build();
        emailer.sendEmailAsync(emailInfo);
        // tag::spel-example-2[]
    }
    // end::spel-example-2[]
}
