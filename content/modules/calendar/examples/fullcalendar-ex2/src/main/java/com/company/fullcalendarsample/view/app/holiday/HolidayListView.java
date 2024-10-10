package com.company.fullcalendarsample.view.app.holiday;

import com.company.fullcalendarsample.entity.Holiday;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "holidays", layout = MainView.class)
@ViewController("Holiday.list")
@ViewDescriptor("holiday-list-view.xml")
@LookupComponent("holidaysDataGrid")
@DialogMode(width = "64em")
public class HolidayListView extends StandardListView<Holiday> {
}