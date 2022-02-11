package dashboards.ex2.screen.bookinstance;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.BookInstance;

@UiController("sample_BookInstance.browse")
@UiDescriptor("book-instance-browse.xml")
@LookupComponent("bookInstancesTable")
public class BookInstanceBrowse extends StandardLookup<BookInstance> {
}