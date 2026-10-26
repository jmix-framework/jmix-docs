package com.company.vaadinspreadsheetex1.view.selection;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import org.apache.poi.ss.util.CellReference;

@Route(value = "spreadsheet-selection-view", layout = MainView.class)
@ViewController(id = "SpreadsheetSelectionView")
@ViewDescriptor(path = "spreadsheet-selection-view.xml")
public class SpreadsheetSelectionView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;
    @ViewComponent
    private Span selectionLabel;

    @Subscribe
    public void onInit(final InitEvent event) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 4; col++) {
                spreadsheet.createCell(row, col, "R%d C%d".formatted(row + 1, col + 1));
            }
        }
    }

    // tag::selection-event[]
    @Subscribe("spreadsheet")
    public void onSpreadsheetSelectionChange(final Spreadsheet.SelectionChangeEvent event) {
        CellReference reference = event.getSelectedCellReference();
        selectionLabel.setText(reference.formatAsString());
    }
    // end::selection-event[]

}
