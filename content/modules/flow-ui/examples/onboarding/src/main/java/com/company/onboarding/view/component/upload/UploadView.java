package com.company.onboarding.view.component.upload;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.FileRejectedEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.UploadSucceededEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "UploadView", layout = MainView.class)
@ViewController("UploadView")
@ViewDescriptor("upload-view.xml")
public class UploadView extends StandardView {

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]
    // tag::UploadSucceededEvent[]
    @Subscribe("upload")
    public void onUploadUploadSucceeded(final UploadSucceededEvent<?> event) {
        notifications.create(
                        "Your file %s has been uploaded successfully.".formatted(
                                event.getFileName()))
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .show();
    }
    // end::UploadSucceededEvent[]

    // tag::FileRejectedEvent[]
    @Subscribe("upload")
    public void onUploadFileRejected(final FileRejectedEvent event) {
        notifications.create(event.getErrorMessage())
                .show();
    }
    // end::FileRejectedEvent[]
}