package dashboards.ex2.screen.author;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Author;

@UiController("sample_Author.edit")
@UiDescriptor("author-edit.xml")
@EditedEntityContainer("authorDc")
public class AuthorEdit extends StandardEditor<Author> {
}