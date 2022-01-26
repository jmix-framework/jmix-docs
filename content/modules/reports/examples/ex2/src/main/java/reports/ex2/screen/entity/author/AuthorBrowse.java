package reports.ex2.screen.entity.author;

import io.jmix.ui.screen.*;
import reports.ex2.entity.Author;

@UiController("jmxrpr_Author.browse")
@UiDescriptor("author-browse.xml")
@LookupComponent("authorsTable")
public class AuthorBrowse extends StandardLookup<Author> {
}