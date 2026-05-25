package com.company.demo.view.attachment;

import com.company.demo.entity.Attachment;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Route(value = "attachments", layout = MainView.class)
@ViewController("Attachment.list")
@ViewDescriptor("attachment-list-view.xml")
@LookupComponent("attachmentsDataGrid")
@DialogMode(width = "50em")
public class AttachmentListView extends StandardListView<Attachment> {

    // tag::files[]
    @ViewComponent
    private DataGrid<Attachment> attachmentsDataGrid;

    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private Downloader downloader; // <1>

    @Subscribe
    public void onInit(final InitEvent event) {
        attachmentsDataGrid.addComponentColumn(attachment -> {
            Button button = uiComponents.create(Button.class);
            button.setText("Download");
            button.addThemeName("tertiary-inline");
            button.addClickListener(clickEvent -> {
                downloader.download(attachment.getFile()); // <2>
            });
            return button;
        });
    }
    // end::files[]

    // tag::file-storage[]
    @Autowired
    private FileStorageLocator fileStorageLocator; // <1>
    @Autowired
    private DataManager dataManager;

    private void getAndSaveImage() {
        try {
            // <2>
            URLConnection connection = new URL("https://picsum.photos/300").openConnection();
            try (InputStream responseStream = connection.getInputStream()) {
                // <3>
                FileStorage fileStorage = fileStorageLocator.getDefault();
                FileRef fileRef = fileStorage.saveStream("photo.jpg", responseStream);
                // <4>
                Attachment attachment = dataManager.create(Attachment.class);
                attachment.setFile(fileRef);
                dataManager.save(attachment);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting image", e);
        }
    }

    private void saveToLocalFile(Attachment attachment, Path path) {
        FileStorage fileStorage = fileStorageLocator.getDefault();
        FileRef fileRef = attachment.getFile();
        // <5>
        InputStream inputStream = fileStorage.openStream(fileRef);
        try {
            // <6>
            Files.copy(inputStream, path.resolve(fileRef.getFileName()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image", e);
        }
    }
    // end::file-storage[]

    @Subscribe("getExternalImageBtn")
    public void onGetExternalImageBtnClick(final ClickEvent event) {
        getAndSaveImage();
    }

    @Subscribe("saveImageBtn")
    public void onSaveImageBtnClick(final ClickEvent event) {
        Attachment attachment = attachmentsDataGrid.getSingleSelectedItem();
        if (attachment == null)
            return;

        saveToLocalFile(attachment, Paths.get("."));
    }

}