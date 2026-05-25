package com.company.onboarding.view.component.fileuploadfield;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.upload.FileUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

@Route(value = "FileUploadFieldView", layout = MainView.class)
@ViewController("FileUploadFieldView")
@ViewDescriptor("file-upload-field-view.xml")
public class FileUploadFieldView extends StandardView {
    private static final Logger log = LoggerFactory.getLogger(FileUploadFieldView.class);

    // tag::FileUploadSucceededEvent[]
    @Subscribe("fileUploadField")
    public void onFileUploadFieldFileUploadSucceeded(
            final FileUploadSucceededEvent<FileUploadField> event) {
        // Access the uploaded file information:
        String fileName = event.getFileName();
        byte[] fileContent = event.getSource().getValue();

        // Perform your logic to handle the fileContent:
        assert fileContent != null;
        log.info("File content: " + new String(fileContent, StandardCharsets.UTF_8));
    }
    // end::FileUploadSucceededEvent[]
}