package com.company.jmixreports.screen.author;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Author;

@UiController("jmxrpr_Author.edit")
@UiDescriptor("author-edit.xml")
@EditedEntityContainer("authorDc")
public class AuthorEdit extends StandardEditor<Author> {
}