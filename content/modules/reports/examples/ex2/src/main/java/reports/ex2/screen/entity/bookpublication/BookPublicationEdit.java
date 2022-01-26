package reports.ex2.screen.entity.bookpublication;

import io.jmix.ui.screen.*;
import reports.ex2.entity.BookPublication;

@UiController("jmxrpr_BookPublication.edit")
@UiDescriptor("book-publication-edit.xml")
@EditedEntityContainer("bookPublicationDc")
public class BookPublicationEdit extends StandardEditor<BookPublication> {
}