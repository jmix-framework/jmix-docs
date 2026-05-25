package com.company.onboarding.view.component.datetimepicker;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.router.Route;
import io.jmix.core.Metadata;
import io.jmix.flowui.component.datetimepicker.TypedDateTimePicker;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

@Route(value = "DateTimePickerView", layout = MainView.class)
@ViewController("DateTimePickerView")
@ViewDescriptor("date-time-picker-view.xml")
public class DateTimePickerView extends StandardView {
    @ViewComponent
    private NativeLabel infoLabel;
    @Autowired
    private Metadata metadata;
    @ViewComponent
    private InstanceContainer<User> userDc;


    //tag::dateTimePicker[]
    @ViewComponent
    private TypedDateTimePicker dateTimePicker;

    //end::dateTimePicker[]

    //tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        //end::onInit[]

        //tag::step[]
        dateTimePicker.setStep(Duration.ofMinutes(30));
        //end::step[]

        //tag::time-range[]
        dateTimePicker.setMin(LocalDateTime.now());
        dateTimePicker.setMax(LocalDateTime.now().plusDays(7));
        //end::time-range[]

        //tag::locale[]
        dateTimePicker.setLocale(Locale.UK);
        //end::locale[]

        User user = metadata.create(User.class);
        user.setPasswordExpiration(LocalDateTime.now().plusYears(1));

        userDc.setItem(user);

        infoLabel.setVisible(true);
        infoLabel.setText("Value in the container: " + userDc.getItem().getPasswordExpiration());
        //tag::onInit[]
    }
    //end::onInit[]

    @Subscribe(id = "userDc", target = Target.DATA_CONTAINER)
    public void onUserDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<User> event) {
        infoLabel.setVisible(true);
        infoLabel.setText("Value in the container: " + event.getValue());
    }


    //tag::validator[]
    @Install(to = "dateTimePickerValidation", subject = "validator")
    private void dateTimePickerValidationValidator(LocalDateTime date) {
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(16,0);

        if (date != null) {
            boolean isWeekday = date.getDayOfWeek().getValue() >= 1 && date.getDayOfWeek().getValue() <= 5;
            boolean isValidTime = !date.toLocalTime().isBefore(startTime) && !date.toLocalTime().isAfter(endTime);
            if (!isWeekday) {
                throw new ValidationException("Select a weekday");
            }
            if (!isValidTime) {
                throw new ValidationException("Select time from 8.00 to 16.00");
            }
        }
    }
    //end::validator[]

}