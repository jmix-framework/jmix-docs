package com.company.notificationsex1.view.order;

import com.company.notificationsex1.entity.Order;

import com.company.notificationsex1.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "orders/:id", layout = MainView.class)
@ViewController("Order_.detail")
@ViewDescriptor("order-detail-view.xml")
@EditedEntityContainer("orderDc")
public class OrderDetailView extends StandardDetailView<Order> {
    // tag::ntf-create[]
    private boolean justCreated;

    @ViewComponent
    private TypedTextField<Integer> numberField;

    @Autowired
    protected NotificationManager notificationManager;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        justCreated = true;
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostSave(final DataContext.PostSaveEvent event) { // <1>
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