package ui.ex1.screen.component.datefield;

import io.jmix.core.TimeSource;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("DateFieldScreen")
@UiDescriptor("date-field-screen.xml")
public class DateFieldScreen extends Screen {
    @Autowired
    protected TimeSource timeSource;
    @Autowired
    protected DateField birthdayField;
    // tag::validator[]
    @Install(to = "birthdayField", subject = "validator")
    protected void birthdayFieldValidator(Date date) {
        Date now = timeSource.currentTimestamp();
        if (DateUtils.addYears(now,-18).compareTo(date) < 0)
            throw new ValidationException("The age must be over 18 years old");
    }
    // end::validator[]

    @Subscribe("validBtn")
    protected void onValidBtnClick(Button.ClickEvent event) {
        birthdayField.validate();
    }
}