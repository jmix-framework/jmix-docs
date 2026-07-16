package com.company.onboarding.view.component.filestorageupload;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadFailedEvent;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Route(value = "FileStorageUploadView", layout = MainView.class)
@ViewController("FileStorageUploadView")
@ViewDescriptor("file-storage-upload-view.xml")
public class FileStorageUploadView extends StandardView {

    private static final Logger log = LoggerFactory.getLogger(FileStorageUploadView.class);

    // tag::autowired[]
    @Autowired
    private TemporaryStorage temporaryStorage;
    @ViewComponent
    private FileStorageUploadField manuallyControlledField;
    @Autowired
    private Notifications notifications;

    // end::autowired[]
    // tag::SucceededEvent[]
    @Subscribe("manuallyControlledField")
    public void onManuallyControlledFieldFileUploadSucceeded(
            final FileUploadSucceededEvent<FileStorageUploadField, TemporaryStorage.FileInfo> event) {
        TemporaryStorage.FileInfo fileInfo = event.getData();
        UUID fileId = fileInfo.getId();
        File file = fileInfo.getFile();

        if (file != null) {
            notifications.create("File is uploaded to temporary storage at "
                            + file.getAbsolutePath())
                    .show();

            FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, event.getFileName());
            manuallyControlledField.setValue(fileRef);
            notifications.create("Uploaded file: " + event.getFileName())
                    .show();
        }
    }

    // end::SucceededEvent[]
    // tag::FailedEvent[]
    @Subscribe("manuallyControlledField")
    public void onManuallyControlledFieldFileUploadFailed(
            final FileUploadFailedEvent<FileStorageUploadField> event) {
        notifications.create("File upload error")
                .show();
    }
    // end::FailedEvent[]
// tag::FileUploadSucceededEvent[]
    @Subscribe("fileRefField")
    public void onFileRefFieldFileUploadSucceeded(
            final FileUploadSucceededEvent<FileStorageUploadField, TemporaryStorage.FileInfo> event) {
        TemporaryStorage.FileInfo fileInfo = event.getData();
        UUID fileId = fileInfo.getId();
        log.info("FileId: " + fileId);

        File file = fileInfo.getFile(); // <1>
        log.info("File from temp storage: " + file);
        try { // <2>
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
            for (String line : lines) {
                log.info("Read line: " + line);
            }
        } catch (MalformedInputException e) {
            notifications.create("Only UTF-8 text files are supported in this example")
                    .show();
        } catch (IOException e) {
            log.error("Unable to read uploaded file {}", event.getFileName(), e);
            notifications.create("Unable to read uploaded file")
                    .show();
        } finally {
            temporaryStorage.deleteFile(fileId); // <3>
            log.info("File is deleted from temp storage: " + file);
        }
    }
    // end::FileUploadSucceededEvent[]
    @Subscribe("fileRefField")
    public void onFileRefFieldFileUploadFailed(final FileUploadFailedEvent<FileStorageUploadField> event) {
        notifications.create("File upload error")
                .show();
    }
}
