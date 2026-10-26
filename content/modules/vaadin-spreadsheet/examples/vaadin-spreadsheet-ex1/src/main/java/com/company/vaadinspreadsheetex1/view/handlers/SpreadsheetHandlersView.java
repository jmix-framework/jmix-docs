package com.company.vaadinspreadsheetex1.view.handlers;

import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.component.spreadsheet.SpreadsheetComponentFactory;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.apache.poi.ss.formula.ConditionalFormattingEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "spreadsheet-handlers-view", layout = MainView.class)
@ViewController(id = "SpreadsheetHandlersView")
@ViewDescriptor(path = "spreadsheet-handlers-view.xml")
public class SpreadsheetHandlersView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;

    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(final InitEvent event) {
        spreadsheet.createCell(0, 0, "Task");
        spreadsheet.createCell(0, 3, "Done");
        spreadsheet.createCell(1, 0, "Write documentation");
        spreadsheet.createCell(1, 3, Boolean.TRUE);
        spreadsheet.createCell(2, 0, "Review examples");
        spreadsheet.createCell(2, 3, Boolean.FALSE);
    }

    // tag::component-factory[]
    @Supply(to = "spreadsheet", subject = "spreadsheetComponentFactory")
    private SpreadsheetComponentFactory spreadsheetComponentFactory() {
        return new SpreadsheetComponentFactory() {

            @Override
            public Component getCustomComponentForCell(Cell cell, int rowIndex, int columnIndex,
                                                       Spreadsheet spreadsheet, Sheet sheet) {
                if (columnIndex == 3 && rowIndex > 0) {
                    Checkbox checkbox = uiComponents.create(Checkbox.class);
                    checkbox.setValue(cell != null && cell.getBooleanCellValue());
                    return checkbox;
                }
                return null; // leave the cell as it is
            }

            @Override
            public Component getCustomEditorForCell(Cell cell, int rowIndex, int columnIndex,
                                                    Spreadsheet spreadsheet, Sheet sheet) {
                return null;
            }

            @Override
            public void onCustomEditorDisplayed(Cell cell, int rowIndex, int columnIndex,
                                                Spreadsheet spreadsheet, Sheet sheet,
                                                Component customEditor) {
            }
        };
    }
    // end::component-factory[]

    @Supply(to = "spreadsheet", subject = "cellDeletionHandler")
    private Spreadsheet.CellDeletionHandler cellDeletionHandler() {
        return new Spreadsheet.CellDeletionHandler() {

            @Override
            public boolean cellDeleted(Cell cell, Sheet sheet, int colIndex, int rowIndex,
                                       FormulaEvaluator formulaEvaluator, DataFormatter formatter,
                                       ConditionalFormattingEvaluator conditionalFormattingEvaluator) {
                return colIndex != 0; // the first column cannot be cleared
            }

            @Override
            public boolean individualSelectedCellsDeleted(List<CellReference> individualSelectedCells,
                                                          Sheet sheet, FormulaEvaluator formulaEvaluator,
                                                          DataFormatter formatter,
                                                          ConditionalFormattingEvaluator conditionalFormattingEvaluator) {
                return individualSelectedCells.stream().noneMatch(ref -> ref.getCol() == 0);
            }

            @Override
            public boolean cellRangeDeleted(List<CellRangeAddress> cellRangeAddresses, Sheet sheet,
                                            FormulaEvaluator formulaEvaluator, DataFormatter formatter,
                                            ConditionalFormattingEvaluator conditionalFormattingEvaluator) {
                return cellRangeAddresses.stream().noneMatch(range -> range.getFirstColumn() == 0);
            }
        };
    }
}
