package dashboards.ex2.screen.bookpublication;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.BookPublication;

@UiController("sample_BookPublication.browse")
@UiDescriptor("book-publication-browse.xml")
@LookupComponent("bookPublicationsTable")
public class BookPublicationBrowse extends StandardLookup<BookPublication> {
}