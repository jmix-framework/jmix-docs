package com.company.vaadinspreadsheetex1.view.basics;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "spreadsheet-basics-view", layout = MainView.class)
@ViewController(id = "SpreadsheetBasicsView")
@ViewDescriptor(path = "spreadsheet-basics-view.xml")
public class SpreadsheetBasicsView extends StandardView {

    // tag::injection[]
    @ViewComponent
    private Spreadsheet spreadsheet;
    // end::injection[]

    @Subscribe
    public void onInit(final InitEvent event) {
        spreadsheet.createCell(0, 0, "Edit me");
    }
}
