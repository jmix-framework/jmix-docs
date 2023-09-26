package com.company.onboarding.view.component.upload;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.upload.*;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.JmixUpload;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "UploadView", layout = MainView.class)
@ViewController("UploadView")
@ViewDescriptor("upload-view.xml")
public class UploadView extends StandardView {
    // tag::upload[]
    @ViewComponent
    private JmixUpload upload;

    // end::upload[]
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
}