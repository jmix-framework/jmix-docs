package reports.ex2.screen.entity.bookinstance;

import io.jmix.ui.screen.*;
import reports.ex2.entity.BookInstance;

@UiController("jmxrpr_BookInstance.browse")
@UiDescriptor("book-instance-browse.xml")
@LookupComponent("bookInstancesTable")
public class BookInstanceBrowse extends StandardLookup<BookInstance> {
}