package reports.ex2.screen.entity.publisher;

import io.jmix.ui.screen.*;
import reports.ex2.entity.Publisher;

@UiController("jmxrpr_Publisher.edit")
@UiDescriptor("publisher-edit.xml")
@EditedEntityContainer("publisherDc")
public class PublisherEdit extends StandardEditor<Publisher> {
}