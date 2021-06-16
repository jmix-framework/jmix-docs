package com.company.jmixreports.screen.publisher;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Publisher;

@UiController("jmxrpr_Publisher.edit")
@UiDescriptor("publisher-edit.xml")
@EditedEntityContainer("publisherDc")
public class PublisherEdit extends StandardEditor<Publisher> {
}