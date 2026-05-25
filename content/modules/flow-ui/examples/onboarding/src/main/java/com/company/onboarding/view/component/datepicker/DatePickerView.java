package com.company.onboarding.view.component.datepicker;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.view.*;
import io.jmix.flowui.exception.ValidationException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;

@Route(value = "DatePickerView", layout = MainView.class)
@ViewController("DatePickerView")
@ViewDescriptor("date-picker-view.xml")
public class DatePickerView extends StandardView {


    //tag::datePicker[]
    @ViewComponent
    private TypedDatePicker<Comparable> datePicker;

    //end::datePicker[]

    @Autowired
    private TimeSource timeSource;

    //tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        //end::onInit[]
        //tag::date-range[]
        datePicker.setMin(LocalDate.now());
        datePicker.setMax(LocalDate.now().plusDays(7));
        //end::date-range[]
        //tag::onInit[]
    }
    //end::onInit[]

    //tag::validator[]
    @Install(to = "birthDatePicker", subject = "validator")
    private void birthDatePickerValidator(Date date) {
            Date now = timeSource.currentTimestamp();
            if (date != null && DateUtils.addYears(now,-18).compareTo(date) < 0) {
                throw new ValidationException("The age must be over 18 years");
            }
    }
    //end::validator[]
}