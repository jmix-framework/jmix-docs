package webdav.ex1.screen.multiupload;

import io.jmix.core.FileRef;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.FileMultiUploadField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.upload.TemporaryStorage;
import io.jmix.webdav.service.WebdavDocumentsManagementService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

@UiController("sample_ScreenWithMultiuploadField")
@UiDescriptor("screen-with-multiupload-field.xml")
public class ScreenWithMultiUploadField extends Screen {
    // tag::autowired[]
    @Autowired
    protected FileMultiUploadField fileMultiUploadFieldVersioned;
    @Autowired
    protected TemporaryStorage temporaryStorage;
    @Autowired
    protected WebdavDocumentsManagementService webdavDocumentsManagementService;
    @Autowired
    protected Notifications notifications;

    // end::autowired[]
    // tag::on-init[]
    @Subscribe
    protected void onInit(InitEvent event) {
        fileMultiUploadFieldVersioned.addQueueUploadCompleteListener(e -> {
            for (Map.Entry<UUID, String> entry :
                    fileMultiUploadFieldVersioned.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();

                FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, fileName); // <1>

                webdavDocumentsManagementService.createVersioningDocumentByFileRef(fileRef); // <2>
            }
            notifications
                    .create()
                    .withType(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Uploaded files: " +
                            fileMultiUploadFieldVersioned.getUploadsMap().values())
                    .show();
            fileMultiUploadFieldVersioned.clearUploads();
        });

        fileMultiUploadFieldVersioned.addFileUploadErrorListener(e ->
                notifications
                        .create()
                        .withCaption("File upload error")
                        .withType(Notifications.NotificationType.HUMANIZED)
                        .show());
    }
    // end::on-init[]
}