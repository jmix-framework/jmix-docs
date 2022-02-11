package dashboards.ex2.screen.book;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Book;

@UiController("sample_Book.browse")
@UiDescriptor("book-browse.xml")
@LookupComponent("booksTable")
public class BookBrowse extends StandardLookup<Book> {
}