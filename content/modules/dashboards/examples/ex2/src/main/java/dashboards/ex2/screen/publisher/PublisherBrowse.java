package dashboards.ex2.screen.publisher;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Publisher;

@UiController("sample_Publisher.browse")
@UiDescriptor("publisher-browse.xml")
@LookupComponent("publishersTable")
public class PublisherBrowse extends StandardLookup<Publisher> {
}