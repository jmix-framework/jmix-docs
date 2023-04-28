package com.company.onboarding.view.component.datepicker;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.action.valuepicker.ValueClearAction;
import io.jmix.flowui.view.*;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Locale;

@Route(value = "DatePickerView", layout = MainView.class)
@ViewController("DatePickerView")
@ViewDescriptor("date-picker-view.xml")
public class DatePickerView extends StandardView {

    @ViewComponent
    private TypedDatePicker datePicker;


    //tag::validator[]
    @Install(to = "datePicker", subject = "validator")
    private void datePickerValidator(LocalDate value) {
        LocalDate today = LocalDate.now();
        if (value != null && today.isAfter(value)) {
            throw new ValidationException("The date must be today or later.");
        }
    }
    //end::validator[]
}