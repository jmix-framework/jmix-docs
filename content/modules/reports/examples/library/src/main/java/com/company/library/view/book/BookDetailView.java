package com.company.library.view.book;

import com.company.library.entity.Book;

import com.company.library.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Actions;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.reportsflowui.action.RunSingleEntityReportAction;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "books/:id", layout = MainView.class)
@ViewController("Book.detail")
@ViewDescriptor("book-detail-view.xml")
@EditedEntityContainer("bookDc")
public class BookDetailView extends StandardDetailView<Book> {
    // tag::actions[]
    @Autowired
    private Actions actions;

    // end::actions[]
    // tag::reportButton[]
    @ViewComponent
    private JmixButton reportButton;

    // end::reportButton[]

    // tag::onInitEntity[]
    @Subscribe
    public void onInitEntity(final InitEntityEvent<Book> event) {
        // end::onInitEntity[]
        // tag::RunSingleEntityReportAction[]
        RunSingleEntityReportAction<Book> action = actions.create(RunSingleEntityReportAction.ID);
        action.setReportOutputName(null);

        reportButton.setAction(action);
        // end::RunSingleEntityReportAction[]
        // tag::onInitEntity[]
    }
    // end::onInitEntity[]
}