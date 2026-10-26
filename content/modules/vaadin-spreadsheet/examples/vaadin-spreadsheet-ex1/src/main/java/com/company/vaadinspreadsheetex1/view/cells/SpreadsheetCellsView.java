package com.company.vaadinspreadsheetex1.view.cells;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

@Route(value = "spreadsheet-cells-view", layout = MainView.class)
@ViewController(id = "SpreadsheetCellsView")
@ViewDescriptor(path = "spreadsheet-cells-view.xml")
public class SpreadsheetCellsView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::create-cells[]
        spreadsheet.createCell(0, 0, "Product");
        spreadsheet.createCell(0, 1, "Amount");
        spreadsheet.createCell(1, 0, "Widget");
        spreadsheet.createCell(1, 1, 1200);
        // end::create-cells[]

        spreadsheet.createCell(2, 0, "Gadget");
        spreadsheet.createCell(2, 1, 850);

        // tag::formula[]
        spreadsheet.createFormulaCell(3, 1, "SUM(B2:B3)");
        // end::formula[]

        // tag::freeze-pane[]
        spreadsheet.createFreezePane(1, 0); // the first row, no columns
        // end::freeze-pane[]
    }

    @Subscribe("updateBtn")
    public void onUpdateBtnClick(final ClickEvent<JmixButton> event) {
        // tag::get-cell[]
        Cell cell = spreadsheet.getCell("B2");
        cell.setCellValue(1500);
        spreadsheet.refreshCells(cell);
        // end::get-cell[]

        spreadsheet.refreshAllCellValues();
    }

    @Subscribe("styleBtn")
    public void onStyleBtnClick(final ClickEvent<JmixButton> event) {
        // tag::styles[]
        Workbook workbook = spreadsheet.getWorkbook();

        CellStyle headerStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);

        CellStyle amountStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        amountStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));

        Cell headerCell = spreadsheet.getCell(0, 0);
        headerCell.setCellStyle(headerStyle);

        Cell amountCell = spreadsheet.getCell(1, 1);
        amountCell.setCellStyle(amountStyle);

        spreadsheet.refreshCells(headerCell, amountCell);
        // end::styles[]
    }
}
