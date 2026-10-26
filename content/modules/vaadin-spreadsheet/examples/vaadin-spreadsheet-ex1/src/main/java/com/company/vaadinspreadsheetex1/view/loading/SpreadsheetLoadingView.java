package com.company.vaadinspreadsheetex1.view.loading;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.core.Resources;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Route(value = "spreadsheet-loading-view", layout = MainView.class)
@ViewController(id = "SpreadsheetLoadingView")
@ViewDescriptor(path = "spreadsheet-loading-view.xml")
public class SpreadsheetLoadingView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;

    @Autowired
    private Resources resources;
    @Autowired
    private Downloader downloader;

    // tag::read[]
    @Subscribe
    public void onInit(final InitEvent event) {
        try (InputStream inputStream =
                     resources.getResourceAsStream("com/company/vaadinspreadsheetex1/budget.xlsx")) {
            spreadsheet.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the budget template", e);
        }
    }
    // end::read[]

    // tag::write[]
    @Subscribe("downloadBtn")
    public void onDownloadBtnClick(final ClickEvent<JmixButton> event) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            spreadsheet.write(outputStream);
            downloader.download(outputStream.toByteArray(), "budget.xlsx", DownloadFormat.XLSX);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write the spreadsheet", e);
        }
    }
    // end::write[]
}
