package ui.ex1.screen.component.filemultiuploadfield;

import io.jmix.core.FileRef;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.FileMultiUploadField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

@UiController("sample_FileMultiUploadFieldScreen")
@UiDescriptor("file-multi-upload-field-screen.xml")
public class FileMultiUploadFieldScreen extends Screen {
    // tag::multi[]
    // <1>
    @Autowired
    private FileMultiUploadField fileMultiUploadField;
    @Autowired
    private TemporaryStorage temporaryStorage;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(InitEvent event) {
        // <2>
        fileMultiUploadField.addQueueUploadCompleteListener(queueUploadCompleteEvent -> { // <3>
            for (Map.Entry<UUID, String> entry : fileMultiUploadField.getUploadsMap().entrySet()) { // <4>
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, fileName); // <5>
            }
            notifications.create()
                    .withCaption("Uploaded files: " + fileMultiUploadField.getUploadsMap().values())
                    .show();
            fileMultiUploadField.clearUploads(); // <6>
        });
        fileMultiUploadField.addFileUploadErrorListener(queueFileUploadErrorEvent ->
                notifications.create()
                        .withCaption("File upload error")
                        .show());
    }
    // end::multi[]
}