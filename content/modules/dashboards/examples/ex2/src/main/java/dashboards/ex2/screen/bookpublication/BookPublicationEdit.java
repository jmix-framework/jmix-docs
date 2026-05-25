package dashboards.ex2.screen.bookpublication;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.BookPublication;

@UiController("sample_BookPublication.edit")
@UiDescriptor("book-publication-edit.xml")
@EditedEntityContainer("bookPublicationDc")
public class BookPublicationEdit extends StandardEditor<BookPublication> {
}