package com.company.library.view.literaturetype;

import com.company.library.entity.LiteratureType;

import com.company.library.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Actions;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.reportsflowui.action.ShowExecutionReportHistoryAction;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "literatureTypes", layout = MainView.class)
@ViewController("LiteratureType.list")
@ViewDescriptor("literature-type-list-view.xml")
@LookupComponent("literatureTypesDataGrid")
@DialogMode(width = "50em")
public class LiteratureTypeListView extends StandardListView<LiteratureType> {
    // tag::historyBtn[]
    @ViewComponent
    private JmixButton historyBtn;

    // end::historyBtn[]
    // tag::actions[]
    @Autowired
    private Actions actions;

    // end::actions[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::ShowExecutionReportHistoryAction[]
        ShowExecutionReportHistoryAction<LiteratureType> action =
                actions.create(ShowExecutionReportHistoryAction.ID);
        historyBtn.setAction(action);
        // end::ShowExecutionReportHistoryAction[]
        // tag::onInit[]
    }
    // end::onInit[]
}