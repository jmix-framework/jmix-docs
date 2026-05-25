package notifications.ex1.listener;

import io.jmix.core.DataManager;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.notifications.NotificationManager;
import io.jmix.notifications.channel.impl.InAppNotificationChannel;
import io.jmix.notifications.entity.ContentType;
import notifications.ex1.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component("sample_OrderEventListener")
public class OrderEventListener {

    /* will be used in https://github.com/jmix-framework/jmix-docs/issues/359

    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    @Autowired
    private DataManager dataManager;
    @Autowired
    protected NotificationManager notificationManager;

    @TransactionalEventListener
    public void onOrderChangedAfterCommit(EntityChangedEvent<Order> event) {
        try {
            if (event.getType() == EntityChangedEvent.Type.CREATED) {
                Order order = dataManager.load(event.getEntityId()).joinTransaction(false).one();

                notificationManager.createNotification() // <3>
                        .withSubject("New order")// <4>
                        .withRecipientUsernames("admin") // <5>
                        .toChannelsByNames(InAppNotificationChannel.NAME) // <6>
                        .withContentType(ContentType.PLAIN) // <7>
                        .withBody("A new order with number " + order.getNumber() + " is created.") // <8>
                        .send(); // <9>
            }
        } catch (Exception e) {
            log.error("Error processing order", e);
        }
    }
     */
}