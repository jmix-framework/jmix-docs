package ui.ex1.screen.facets.notificationfacet;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_NotificationFacetScreen")
@UiDescriptor("notification-facet-screen.xml")
public class NotificationFacetScreen extends Screen {
    // tag::close-event[]
    private static final Logger log = LoggerFactory.getLogger(NotificationFacetScreen.class);

    @Subscribe("actionNotification")
    public void onActionNotificationClose(Notifications.CloseEvent event) {
        log.info("Notification was closed");
    }
    // end::close-event[]

    // tag::caption-provider[]
    @Install(to = "actionNotification", subject = "captionProvider")
    private String actionNotificationCaptionProvider() {
        return "Changed caption";
    }
    // end::caption-provider[]

    // tag::description-provider[]
    @Install(to = "actionNotification", subject = "descriptionProvider")
    private String actionNotificationDescriptionProvider() {
        return "Changed description";
    }
    // end::description-provider[]

    // tag::show[]
    @Autowired
    private NotificationFacet notificationFacet;

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        notificationFacet.show();
    }
    // end::show[]
}