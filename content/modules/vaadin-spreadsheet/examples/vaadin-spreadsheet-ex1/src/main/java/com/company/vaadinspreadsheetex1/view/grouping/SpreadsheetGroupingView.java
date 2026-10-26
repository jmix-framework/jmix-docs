package com.company.vaadinspreadsheetex1.view.grouping;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.core.Resources;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

@Route(value = "spreadsheet-grouping-view", layout = MainView.class)
@ViewController(id = "SpreadsheetGroupingView")
@ViewDescriptor(path = "spreadsheet-grouping-view.xml")
public class SpreadsheetGroupingView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;

    @Autowired
    private Resources resources;

    // tag::load-grouped[]
    @Subscribe
    public void onInit(final InitEvent event) {
        try (InputStream inputStream =
                     resources.getResourceAsStream("com/company/vaadinspreadsheetex1/countries.xlsx")) {
            spreadsheet.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read the countries document", e);
        }
    }
    // end::load-grouped[]

    // tag::grouping[]
    @Subscribe("groupBtn")
    public void onGroupBtnClick(final ClickEvent<JmixButton> event) {
        Sheet sheet = spreadsheet.getActiveSheet();
        sheet.groupColumn(1, 2); // columns B and C

        spreadsheet.reload();
    }
    // end::grouping[]
}
