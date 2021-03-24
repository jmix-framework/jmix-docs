package com.company.jmixreports.screen.town;

import io.jmix.reportsui.action.list.RunReportAction;
import io.jmix.ui.Actions;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Town;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("jmxrpr_Town.browse")
@UiDescriptor("town-browse.xml")
@LookupComponent("townsTable")
public class TownBrowse extends StandardLookup<Town> {
    // tag::run-report-button[]
    @Autowired
    private Button runReportBtn;

    @Autowired
    private Actions actions;

    @Subscribe
    public void onInit(InitEvent event) {
        RunReportAction action = actions.create(RunReportAction.class, "runReport");

        runReportBtn.setAction(action);
    }
    // end::run-report-button[]

}