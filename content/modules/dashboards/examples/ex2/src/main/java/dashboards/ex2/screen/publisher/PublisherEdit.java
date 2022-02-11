package dashboards.ex2.screen.publisher;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Publisher;

@UiController("sample_Publisher.edit")
@UiDescriptor("publisher-edit.xml")
@EditedEntityContainer("publisherDc")
public class PublisherEdit extends StandardEditor<Publisher> {
}