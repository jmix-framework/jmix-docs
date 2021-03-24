package com.company.jmixreports.screen.book;

import io.jmix.reportsui.action.list.EditorPrintFormAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Book;

import javax.inject.Inject;

@UiController("jmxrpr_Book.edit")
@UiDescriptor("book-edit.xml")
@EditedEntityContainer("bookDc")
public class BookEdit extends StandardEditor<Book> {
    // tag::editor-print-button[]
    @Inject
    private Button reportButton;

    @Subscribe
    public void onInit(InitEvent event) {
        reportButton.setAction(new EditorPrintFormAction(this,null));
    }
    // end::editor-print-button[]
}