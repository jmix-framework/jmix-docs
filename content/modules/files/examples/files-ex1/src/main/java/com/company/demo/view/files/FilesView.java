package com.company.demo.view.files;


import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.upload.TemporaryStorageManagementFacade;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

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
    public void onFileFieldFileUploadSucceeded(
            FileUploadSucceededEvent<FileStorageUploadField, TemporaryStorage.FileInfo> event) {
        TemporaryStorage.FileInfo fileInfo = event.getData();
        File file = fileInfo.getFile(); // <1>
        // here you have full access to the file
        FileRef fileRef = temporaryStorage.putFileIntoStorage(
                fileInfo.getId(), event.getFileName()); // <2>
        fileField.setValue(fileRef);
    }
    //end::temporary-storage-2[]


    //tag::get-and-delete[]
    private void processAndDeleteFile(TemporaryStorage.FileInfo fileInfo) {
        processFile(fileInfo.getFile());
        temporaryStorage.deleteFile(fileInfo.getId());
    }
    //end::get-and-delete[]

    private void processFile(File file) {
    }
}