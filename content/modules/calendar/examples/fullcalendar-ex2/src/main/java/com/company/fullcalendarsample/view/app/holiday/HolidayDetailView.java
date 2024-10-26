package com.company.fullcalendarsample.view.app.holiday;

import com.company.fullcalendarsample.entity.Holiday;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "holidays/:id", layout = MainView.class)
@ViewController("Holiday.detail")
@ViewDescriptor("holiday-detail-view.xml")
@EditedEntityContainer("holidayDc")
public class HolidayDetailView extends StandardDetailView<Holiday> {
}