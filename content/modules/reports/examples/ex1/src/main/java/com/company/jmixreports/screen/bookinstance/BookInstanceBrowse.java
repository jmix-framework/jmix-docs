package com.company.jmixreports.screen.bookinstance;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.BookInstance;

@UiController("jmxrpr_BookInstance.browse")
@UiDescriptor("book-instance-browse.xml")
@LookupComponent("bookInstancesTable")
public class BookInstanceBrowse extends StandardLookup<BookInstance> {
}