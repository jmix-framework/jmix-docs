package com.company.jmixreports.screen.book;

import io.jmix.reportsui.action.list.EditorPrintFormAction;
import io.jmix.ui.Actions;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

@UiController("jmxrpr_Book.edit")
@UiDescriptor("book-edit.xml")
@EditedEntityContainer("bookDc")
public class BookEdit extends StandardEditor<Book> {
    // tag::editor-print-button[]
    @Inject
    private Button reportButton;

    @Autowired
    private Actions actions;

    @Subscribe
    public void onInit(InitEvent event) {
//        EditorPrintFormAction action = actions.create(EditorPrintFormAction.class, "editPrint");
        EditorPrintFormAction action = actions.create(EditorPrintFormAction.class);
        reportButton.setAction(action);
    }
    // end::editor-print-button[]
}