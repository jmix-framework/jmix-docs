package com.company.onboarding.view.component.filestorageupload;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.kit.component.upload.event.*;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.reflections.Reflections.log;

@Route(value = "FileStorageUploadView", layout = MainView.class)
@ViewController("FileStorageUploadView")
@ViewDescriptor("file-storage-upload-view.xml")
public class FileStorageUploadView extends StandardView {
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
            final FileUploadSucceededEvent<FileStorageUploadField> event) {
        Receiver receiver = event.getReceiver();
        if (receiver instanceof FileTemporaryStorageBuffer) {
            UUID fileId = ((FileTemporaryStorageBuffer) receiver)
                    .getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileId);

            if (file != null) {
                notifications.create("File is uploaded to temporary storage at "
                                + file.getAbsolutePath())
                        .show();
            }
            FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, event.getFileName());
            manuallyControlledField.setValue(fileRef);
            notifications.create("Uploaded file: "
                            + ((FileTemporaryStorageBuffer) receiver).getFileData().getFileName())
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
            final FileUploadSucceededEvent<FileStorageUploadField> event) {
        if (event.getReceiver() instanceof FileTemporaryStorageBuffer buffer) {
            UUID fileId = buffer.getFileData().getFileInfo().getId();
            log.info("FileId: " + fileId);

            File file = temporaryStorage.getFile(fileId); // <1>
            log.info("File from temp storage: " + file);
            try { // <2>
                List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
                for (String line : lines) {
                    log.info("Read line: " + line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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