package bpm.ex1.listener;

import bpm.ex1.entity.Order;
import bpm.ex1.entity.User;
import io.jmix.bpm.engine.events.UserTaskAssignedEvent;
import io.jmix.core.DataManager;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("smpl_TaskAssignedNotificationSender2")
public class TaskAssignedNotificationSender2 {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private Emailer emailer;

    // tag::get-variable-1[]
    @Autowired
    private RuntimeService runtimeService;

    @EventListener
    public void onOtherProcessTaskAssigned(UserTaskAssignedEvent event) {
        Order order = (Order) runtimeService.getVariable(event.getTask().getExecutionId(), "order");
        // ...
        // end::get-variable-1[]
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
        // tag::get-variable-2[]
    }
// end::get-variable-2[]
}

