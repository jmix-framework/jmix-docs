package com.company.jmixreports.screen.bookpublication;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.BookPublication;

@UiController("jmxrpr_BookPublication.browse")
@UiDescriptor("book-publication-browse.xml")
@LookupComponent("bookPublicationsTable")
public class BookPublicationBrowse extends StandardLookup<BookPublication> {
}