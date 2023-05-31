package com.company.onboarding.view.component.datepicker;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.view.*;
import io.jmix.flowui.exception.ValidationException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

@Route(value = "DatePickerView", layout = MainView.class)
@ViewController("DatePickerView")
@ViewDescriptor("date-picker-view.xml")
public class DatePickerView extends StandardView {

    @Autowired
    private TimeSource timeSource;

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