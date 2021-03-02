package com.company.jmixreports.screen.book;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Book;

@UiController("jmxrpr_Book.edit")
@UiDescriptor("book-edit.xml")
@EditedEntityContainer("bookDc")
public class BookEdit extends StandardEditor<Book> {
}