package notifications.ex1.screen.order;

import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import notifications.ex1.entity.Order;
import notifications.ex1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ntf_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {

    // tag::ntf-create[]
    private boolean justCreated;

    @Autowired
    private TextField<String> numberField;

    @Autowired
    protected NotificationManager notificationManager;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        justCreated = true;
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) { // <1>
        if (justCreated) { // <2>
            notificationManager.createNotification() // <3>
                    .withSubject("New order")// <4>
                    .withRecipientUsernames("admin") // <5>
                    .toChannelsByNames("in-app") // <6>
                    .withContentType(ContentType.PLAIN) // <7>
                    .withBody("A new order with number " + numberField.getValue()+ " is created.") // <8>
                    .send(); // <9>
        }
    }
    // end::ntf-create[]
}