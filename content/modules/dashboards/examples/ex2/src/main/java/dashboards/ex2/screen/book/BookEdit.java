package dashboards.ex2.screen.book;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Book;

@UiController("sample_Book.edit")
@UiDescriptor("book-edit.xml")
@EditedEntityContainer("bookDc")
public class BookEdit extends StandardEditor<Book> {
}