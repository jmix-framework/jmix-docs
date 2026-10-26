package com.company.vaadinspreadsheetex1.view.entitydata;

import com.company.vaadinspreadsheetex1.entity.User;
import com.company.vaadinspreadsheetex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.spreadsheet.Spreadsheet;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.*;
import org.apache.poi.ss.usermodel.Cell;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Route(value = "spreadsheet-entity-data-view", layout = MainView.class)
@ViewController(id = "SpreadsheetEntityDataView")
@ViewDescriptor(path = "spreadsheet-entity-data-view.xml")
public class SpreadsheetEntityDataView extends StandardView {

    @ViewComponent
    private Spreadsheet spreadsheet;
    @ViewComponent
    private DataContext dataContext;

    // tag::fill[]
    @ViewComponent
    private CollectionContainer<User> usersDc;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        spreadsheet.createCell(0, 0, "Username");
        spreadsheet.createCell(0, 1, "First name");
        spreadsheet.createCell(0, 2, "Last name");

        List<User> users = usersDc.getItems();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            int row = i + 1;
            spreadsheet.createCell(row, 0, user.getUsername());
            spreadsheet.createCell(row, 1, user.getFirstName());
            spreadsheet.createCell(row, 2, user.getLastName());
        }

        spreadsheet.createFreezePane(1, 0);
    }
    // end::fill[]

    // tag::save-to-entities[]
    @Subscribe("saveBtn")
    public void onSaveBtnClick(final ClickEvent<JmixButton> event) {
        List<User> users = usersDc.getItems();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            int row = i + 1;
            user.setFirstName(getCellValue(row, 1));
            user.setLastName(getCellValue(row, 2));
        }
        dataContext.save();
    }

    @Nullable
    private String getCellValue(int row, int column) {
        Cell cell = spreadsheet.getCell(row, column);
        return cell != null
                // DataFormatter returns the text of a cell whatever its type
                ? spreadsheet.getDataFormatter().formatCellValue(cell)
                : null;
    }
    // end::save-to-entities[]
}
