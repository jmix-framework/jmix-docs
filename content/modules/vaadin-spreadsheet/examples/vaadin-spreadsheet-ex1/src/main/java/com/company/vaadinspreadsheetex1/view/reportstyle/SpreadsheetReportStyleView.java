package com.company.vaadinspreadsheetex1.view.reportstyle;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.core.Resources;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

@Route(value = "spreadsheet-report-style-view", layout = MainView.class)
@ViewController(id = "SpreadsheetReportStyleView")
@ViewDescriptor(path = "spreadsheet-report-style-view.xml")
public class SpreadsheetReportStyleView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;

    @Autowired
    private Resources resources;

    @Subscribe
    public void onInit(final InitEvent event) {
        try (InputStream inputStream =
                     resources.getResourceAsStream("com/company/vaadinspreadsheetex1/budget.xlsx")) {
            spreadsheet.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the budget template", e);
        }

        // tag::clean-output[]
        spreadsheet.setGridlinesVisible(false);
        spreadsheet.setRowColHeadingsVisible(false);
        spreadsheet.setActiveSheetProtected("");
        // end::clean-output[]
    }
}
