package reports.ex2.screen.entity.book;

import io.jmix.ui.screen.*;
import reports.ex2.entity.Book;

@UiController("jmxrpr_Book.browse")
@UiDescriptor("book-browse.xml")
@LookupComponent("booksTable")
public class BookBrowse extends StandardLookup<Book> {
}