package com.company.clientapp.view.region;

import com.company.clientapp.entity.Region;
import com.company.clientapp.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "regions", layout = MainView.class)
@ViewController("Region.list")
@ViewDescriptor("region-list-view.xml")
@LookupComponent("regionsDataGrid")
@DialogMode(width = "50em")
public class RegionListView extends StandardListView<Region> {
}
