package com.company.jmixreports.screen.book;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Book;

@UiController("jmxrpr_Book.browse")
@UiDescriptor("book-browse.xml")
@LookupComponent("booksTable")
public class BookBrowse extends StandardLookup<Book> {
}