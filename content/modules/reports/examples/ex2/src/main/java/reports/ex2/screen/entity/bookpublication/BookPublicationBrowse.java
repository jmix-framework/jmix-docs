package reports.ex2.screen.entity.bookpublication;

import io.jmix.reportsui.action.list.ExecutionHistoryAction;
import io.jmix.ui.Actions;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import reports.ex2.entity.BookPublication;

import javax.inject.Inject;

@UiController("jmxrpr_BookPublication.browse")
@UiDescriptor("book-publication-browse.xml")
@LookupComponent("bookPublicationsTable")
public class BookPublicationBrowse extends StandardLookup<BookPublication> {
    // tag::report-history-button[]
    @Inject
    protected Actions actions;

    @Inject
    protected Button execHistoryBtn;

    @Subscribe
    public void onInit(InitEvent event) {
        ExecutionHistoryAction action = actions.create(ExecutionHistoryAction.class, "execHistoryReport");
        execHistoryBtn.setAction(action);
    }
    // end::report-history-button[]
}