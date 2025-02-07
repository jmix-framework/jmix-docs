package com.company.sample.view.booking;

import com.company.sample.entity.Booking;
import com.company.sample.entity.User;
import com.company.sample.view.main.MainView;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;
import io.jmix.email.EmailException;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import freemarker.template.Configuration;
import io.jmix.messagetemplates.MessageTemplateProperties;
import io.jmix.messagetemplates.MessageTemplatesGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Route(value = "bookings", layout = MainView.class)
@ViewController(id = "Booking.list")
@ViewDescriptor(path = "booking-list-view.xml")
@LookupComponent("bookingsDataGrid")
@DialogMode(width = "64em")
public class BookingListView extends StandardListView<Booking> {
    // tag::booking-list-view[]
    @ViewComponent
    private DataGrid<Booking> bookingsDataGrid;
    @Autowired
    private MessageTemplateProperties messageTemplateProperties; // <1>
    @Autowired
    private MessageTemplatesGenerator messageTemplatesGenerator; // <2>
    @Autowired
    private Emailer emailer; // <3>
    @Autowired
    private Notifications notifications; // <4>

    @Subscribe("bookingsDataGrid.notifyEmail")
    public void onBookingsDataGridNotifyEmail(final ActionPerformedEvent event) {
        Booking booking = bookingsDataGrid.getSingleSelectedItem();
        User creator = booking.getCreator(); // <5>

        String email = creator.getEmail();
        if (email == null) {
            showNoEmailNotification(creator); // <6>
            return;
        }

        Configuration configuration =
                new Configuration(messageTemplateProperties.getFreemarkerVersion());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDateFormat("yyyy, MM/dd"); // <7>

        List<String> messages = messageTemplatesGenerator.generateMultiTemplate()
                .withTemplateCodes("booking-email-subject", "booking-email-body") // <8>
                .withParams(
                        Map.of(
                                "booking", booking,
                                "today", new Date(),
                                "penalty", 5000
                        )) // <9>
                .withConfiguration(configuration)
                .generate();

        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses(email)
                .setSubject(messages.get(0))
                .setBody(messages.get(1))
                .setBodyContentType("text/html; charset=UTF-8")
                .build(); // <10>

        try {
            emailer.sendEmail(emailInfo); // <11>
        } catch (EmailException e) {
            showSendingErrorNotification(email);
        }

        showSendingSuccessNotification(email); // <12>
    }

    private void showSendingErrorNotification(String email) {
        notifications.create("Failed to send email to %s".formatted(email))
                .withThemeVariant(NotificationVariant.LUMO_ERROR)
                .show();
    }

    private void showNoEmailNotification(User creator) {
        notifications.create("%s did not specify an email".formatted(creator.getDisplayName()))
                .withThemeVariant(NotificationVariant.LUMO_ERROR)
                .show();
    }

    private void showSendingSuccessNotification(String email) {
        notifications.create("The message has been successfully sent to %s".formatted(email))
                .withThemeVariant(NotificationVariant.LUMO_SUCCESS)
                .show();
    }
    // end::booking-list-view[]
}