package reports.ex2.screen.entity.bookinstance;

import io.jmix.ui.screen.*;
import reports.ex2.entity.BookInstance;

@UiController("jmxrpr_BookInstance.edit")
@UiDescriptor("book-instance-edit.xml")
@EditedEntityContainer("bookInstanceDc")
public class BookInstanceEdit extends StandardEditor<BookInstance> {
}