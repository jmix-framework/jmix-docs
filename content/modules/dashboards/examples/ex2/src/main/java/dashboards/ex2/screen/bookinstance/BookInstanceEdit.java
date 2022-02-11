package dashboards.ex2.screen.bookinstance;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.BookInstance;

@UiController("sample_BookInstance.edit")
@UiDescriptor("book-instance-edit.xml")
@EditedEntityContainer("bookInstanceDc")
public class BookInstanceEdit extends StandardEditor<BookInstance> {
}