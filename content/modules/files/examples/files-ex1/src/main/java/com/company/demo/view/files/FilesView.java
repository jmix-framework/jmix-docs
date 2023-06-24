package com.company.demo.view.files;


import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.upload.TemporaryStorageManagementFacade;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

@Route(value = "FilesView", layout = MainView.class)
@ViewController("FilesView")
@ViewDescriptor("files-view.xml")
public class FilesView extends StandardView {

    //tag::temporary-storage-1[]
    @ViewComponent
    private FileStorageUploadField fileField;

    @Autowired
    private TemporaryStorage temporaryStorage;

    //end::temporary-storage-1[]

    //tag::temporary-storage-clean-1[]
    @Autowired
    private TemporaryStorageManagementFacade storageFacade;

    //end::temporary-storage-clean-1[]

    //tag::temporary-storage-2[]
    @Subscribe("fileField")
    public void onFileFieldFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) {
        if (event.getReceiver() instanceof FileTemporaryStorageBuffer buffer) {
            UUID fileId = buffer.getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileId); // <1>
            if (file != null) {
                FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, event.getFileName()); // <2>
                fileField.setValue(fileRef);
            }
        }
    }
    //end::temporary-storage-2[]


    private void processAndDeleteFile(UUID fileId) throws FileNotFoundException {
        //tag::get-and-delete[]
        File file = temporaryStorage.getFile(fileId);
        if (file != null) {
            processFile(file);
            temporaryStorage.deleteFile(fileId);
        }
        //end::get-and-delete[]

        //tag::temporary-storage-clean-2[]
        storageFacade.clearTempDirectory();
        //end::temporary-storage-clean-2[]
    }

    private void processFile(File file) {
    }
}