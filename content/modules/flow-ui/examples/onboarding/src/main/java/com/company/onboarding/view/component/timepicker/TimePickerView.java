package com.company.onboarding.view.component.timepicker;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.timepicker.TypedTimePicker;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;

@Route(value = "TimePickerView", layout = MainView.class)
@ViewController("TimePickerView")
@ViewDescriptor("time-picker-view.xml")
public class TimePickerView extends StandardView {

    //tag::timePicker[]
    @ViewComponent
    private TypedTimePicker<Comparable> timePicker;

    //end::timePicker[]

    //tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        //end::onInit[]
        //tag::step[]
        timePicker.setStep(Duration.ofMinutes(30));
        //end::step[]

        //tag::locale[]
        timePicker.setLocale(Locale.UK);
        //end::locale[]

        //tag::time-range[]
        timePicker.setMin(LocalTime.of(8,0));
        timePicker.setMax(LocalTime.of(17,0));
        //end::time-range[]
        //tag::onInit[]
    }
    //end::onInit[]

    //tag::validator[]
    @Install(to = "timePicker", subject = "validator")
    private void timePickerValidator(LocalTime value) {
        if (value != null && LocalTime.of(13,0).isBefore(value) && LocalTime.of(14,0).isAfter(value)) {
            throw new ValidationException("No appointments between 13:00 to 14:00.");
        }
    }
    //end::validator[]

}