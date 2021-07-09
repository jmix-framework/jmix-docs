package com.company.jmixreports.screen.bookinstance;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.BookInstance;

@UiController("jmxrpr_BookInstance.edit")
@UiDescriptor("book-instance-edit.xml")
@EditedEntityContainer("bookInstanceDc")
public class BookInstanceEdit extends StandardEditor<BookInstance> {
}