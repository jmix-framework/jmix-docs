package reports.ex2.screen.entity.author;

import io.jmix.ui.screen.*;
import reports.ex2.entity.Author;

@UiController("jmxrpr_Author.edit")
@UiDescriptor("author-edit.xml")
@EditedEntityContainer("authorDc")
public class AuthorEdit extends StandardEditor<Author> {
}