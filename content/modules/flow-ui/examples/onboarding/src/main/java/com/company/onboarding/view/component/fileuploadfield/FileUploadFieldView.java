package com.company.onboarding.view.component.fileuploadfield;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "FileUploadFieldView", layout = MainView.class)
@ViewController("FileUploadFieldView")
@ViewDescriptor("file-upload-field-view.xml")
public class FileUploadFieldView extends StandardView {
}