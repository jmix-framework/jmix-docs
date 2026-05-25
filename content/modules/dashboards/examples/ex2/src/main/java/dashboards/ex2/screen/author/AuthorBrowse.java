package dashboards.ex2.screen.author;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Author;

@UiController("sample_Author.browse")
@UiDescriptor("author-browse.xml")
@LookupComponent("authorsTable")
public class AuthorBrowse extends StandardLookup<Author> {
}