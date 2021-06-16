package com.company.jmixreports.screen.publisher;

import io.jmix.reportsui.action.list.ListPrintFormAction;
import io.jmix.ui.Actions;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Publisher;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("jmxrpr_Publisher.browse")
@UiDescriptor("publisher-browse.xml")
@LookupComponent("publishersTable")
public class PublisherBrowse extends StandardLookup<Publisher> {

    // tag::list-print-button[]
    @Autowired
    private Button listPrintFormBtn;

    @Autowired
    private Actions actions;

    @Subscribe
    public void onInit(InitEvent event) {
        ListPrintFormAction action = actions.create(ListPrintFormAction.class, "listPrintForm");
        listPrintFormBtn.setAction(action);
    }
    // end::list-print-button[]

}