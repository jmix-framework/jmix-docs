package ui.ex1.screen.screens.validate;

import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Event;

// tag::demo-screen[]
@UiController("sample_DemoScreen")
@UiDescriptor("demo-screen.xml")
public class DemoScreen extends Screen {

    // end::demo-screen[]

    @Autowired
    private Action windowClose;

    // tag::screen-validation[]
    @Autowired
    private ScreenValidation screenValidation;

    // end::screen-validation[]

    // tag::validate-cross[]
    @Autowired
    protected Metadata metadata;

    @Autowired
    protected TimeSource timeSource;

    // end::validate-cross[]

    // tag::validate-ui[]
    @Autowired
    private Form demoForm;

    @Subscribe("validateBtn")
    public void onValidateBtnClick(Button.ClickEvent event) {
        ValidationErrors errors = screenValidation.validateUiComponents(demoForm);
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
            return;
        }
    }
    // end::validate-ui[]

    // tag::validate-date[]
    @Subscribe("validateDateBtn")
    public void onValidateDateBtnClick(Button.ClickEvent event) {
        Event demoEvent = metadata.create(Event.class);
        demoEvent.setName("Demo event");
        demoEvent.setStartDate(timeSource.currentTimestamp());
        demoEvent.setEndDate(DateUtils.addDays(demoEvent.getStartDate(), -1));
        ValidationErrors errors = screenValidation.validateCrossFieldRules(this, demoEvent);
        if (!errors.isEmpty()) {
            screenValidation.showValidationErrors(this, errors);
        }
    }
    // end::validate-date[]
    // tag::end[]
}
// end::end[]