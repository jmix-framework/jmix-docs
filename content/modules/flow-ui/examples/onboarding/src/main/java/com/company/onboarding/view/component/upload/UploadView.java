package com.company.onboarding.view.component.upload;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.*;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.JmixUpload;
import io.jmix.flowui.view.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

@Route(value = "UploadView", layout = MainView.class)
@ViewController("UploadView")
@ViewDescriptor("upload-view.xml")
public class UploadView extends StandardView {

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::FileRejectedEvent[]
    @Subscribe("upload")
    public void onUploadFileRejected(final FileRejectedEvent event) {
        notifications.create(event.getErrorMessage())
                .show();
    }
    // end::FileRejectedEvent[]
    // tag::SucceededEvent[]
    @Subscribe("upload")
    public void onUploadSucceeded(final SucceededEvent event) {
        notifications.create(
                "Your file %s has been uploaded successfully.".formatted(
                        event.getFileName()))
                .withThemeVariant(NotificationVariant.LUMO_PRIMARY)
                .show();
    }
    // end::SucceededEvent[]
}