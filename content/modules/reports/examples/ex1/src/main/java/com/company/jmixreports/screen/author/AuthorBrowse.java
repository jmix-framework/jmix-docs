package com.company.jmixreports.screen.author;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Author;

@UiController("jmxrpr_Author.browse")
@UiDescriptor("author-browse.xml")
@LookupComponent("authorsTable")
public class AuthorBrowse extends StandardLookup<Author> {
}