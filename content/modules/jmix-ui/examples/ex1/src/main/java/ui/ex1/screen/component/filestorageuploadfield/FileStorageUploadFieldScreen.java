package ui.ex1.screen.component.filestorageuploadfield;

import io.jmix.core.FileRef;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.FileStorageUploadField;
import io.jmix.ui.component.SingleFileUploadField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

@UiController("sample_FileStorageUploadFieldScreen")
@UiDescriptor("file-storage-upload-field-screen.xml")
public class FileStorageUploadFieldScreen extends Screen {
    // tag::manually[]
    @Autowired
    private FileStorageUploadField manuallyControlledField;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @Autowired
    private Notifications notifications;

    @Subscribe("manuallyControlledField")
    public void onManuallyControlledFieldFileUploadSucceed(SingleFileUploadField.FileUploadSucceedEvent event) {
        File file = temporaryStorage.getFile(manuallyControlledField.getFileId()); // <1>
        if (file != null) {
            notifications.create()
                    .withCaption("File is uploaded to temporary storage at " + file.getAbsolutePath())
                    .show();
        }
        // <2>
        FileRef fileRef = temporaryStorage.putFileIntoStorage(manuallyControlledField.getFileId(), event.getFileName()); // <3>
        manuallyControlledField.setValue(fileRef);
        notifications.create()
                .withCaption("Uploaded file: " + manuallyControlledField.getFileName())
                .show();
    }

    @Subscribe("manuallyControlledField")
    public void onManuallyControlledFieldFileUploadError(SingleFileUploadField.FileUploadErrorEvent event) {
        notifications.create()
                .withCaption("File upload error")
                .show();
    }
    // end::manually[]
}