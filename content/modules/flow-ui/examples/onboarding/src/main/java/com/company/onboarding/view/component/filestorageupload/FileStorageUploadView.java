package com.company.onboarding.view.component.filestorageupload;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "FileStorageUploadView", layout = MainView.class)
@ViewController("FileStorageUploadView")
@ViewDescriptor("file-storage-upload-view.xml")
public class FileStorageUploadView extends StandardView {
}