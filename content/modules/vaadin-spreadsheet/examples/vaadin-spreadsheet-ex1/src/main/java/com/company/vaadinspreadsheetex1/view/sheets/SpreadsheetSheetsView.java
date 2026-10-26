package com.company.vaadinspreadsheetex1.view.sheets;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "spreadsheet-sheets-view", layout = MainView.class)
@ViewController(id = "SpreadsheetSheetsView")
@ViewDescriptor(path = "spreadsheet-sheets-view.xml")
public class SpreadsheetSheetsView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;

    @Subscribe("addSheetBtn")
    public void onAddSheetBtnClick(final ClickEvent<JmixButton> event) {
        // tag::sheets[]
        spreadsheet.createNewSheet("Summary", 100, 20); // name, rows, columns
        spreadsheet.setSheetName(1, "Details");
        spreadsheet.setActiveSheetIndex(1);
        // end::sheets[]

        spreadsheet.createCell(0, 0, "Created programmatically");
    }

    @Subscribe("deleteSheetBtn")
    public void onDeleteSheetBtnClick(final ClickEvent<JmixButton> event) {
        // tag::delete-sheet[]
        if (spreadsheet.getNumberOfSheets() > 1) {
            spreadsheet.deleteSheet(spreadsheet.getActiveSheetIndex());
        }
        // end::delete-sheet[]
    }
}
