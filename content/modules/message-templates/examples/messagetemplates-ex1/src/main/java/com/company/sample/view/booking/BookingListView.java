package com.company.sample.view.booking;

import com.company.sample.entity.Booking;
import com.company.sample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "bookings", layout = MainView.class)
@ViewController(id = "Booking.list")
@ViewDescriptor(path = "booking-list-view.xml")
@LookupComponent("bookingsDataGrid")
@DialogMode(width = "64em")
public class BookingListView extends StandardListView<Booking> {
}