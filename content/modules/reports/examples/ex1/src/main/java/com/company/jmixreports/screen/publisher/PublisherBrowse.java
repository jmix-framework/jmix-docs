package com.company.jmixreports.screen.publisher;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Publisher;

@UiController("jmxrpr_Publisher.browse")
@UiDescriptor("publisher-browse.xml")
@LookupComponent("publishersTable")
public class PublisherBrowse extends StandardLookup<Publisher> {
}