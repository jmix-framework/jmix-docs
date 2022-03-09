package ui.ex1.components.javascript;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.JavaScriptComponent;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

// tag::start[]
@UiController("sample_JavaScriptComponentSample")
@UiDescriptor("java-script-component-sample.xml")
public class JavaScriptComponentSample extends Screen {

    @Autowired
    protected JavaScriptComponent timePicker;

    @Autowired
    protected Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        // end::start[]
        // tag::add-dependencies[]
        timePicker.addDependencies(
                "webjar://jquery:jquery.min.js",
                "vaadin://timepicker/wickedpicker.min.js",
                "vaadin://timepicker/time-picker-connector.js");
        timePicker.addDependency("vaadin://timepicker/wickedpicker.min.css",
                JavaScriptComponent.DependencyType.STYLESHEET);
        // end::add-dependencies[]
        // tag::set-init-function-name[]
        timePicker.setInitFunctionName("ui_ex1_components_javascript_TimePicker");
        // end::set-init-function-name[]
        // tag::end[]
        // tag::state[]
        TimePickerState state = new TimePickerState();

        state.now = "12:35:57";
        state.showSeconds = true;
        state.twentyFour = true;

        timePicker.setState(state);
        // end::state[]
        // tag::register-function[]
        timePicker.addFunction("onBeforeShow", callbackEvent ->
                notifications.create()
                        .withCaption("Before Show Event")
                        .withPosition(Notifications.Position.MIDDLE_RIGHT)
                        .show());
        // end::register-function[]
        timePicker.addFunction("onShow", callbackEvent ->
                notifications.create()
                        .withCaption("Show Event")
                        .show());
    }

    @Subscribe("showValueBtn")
    protected void onShowValueBtnClick(Button.ClickEvent event) {
        // tag::call-function[]
        timePicker.callFunction("showValue");
        // end::call-function[]
    }

    public class TimePickerState implements Serializable {
        public String now;             // hh:mm 24 hour format only, defaults to current time
        public boolean twentyFour;     // Display 24 hour format, defaults to false
        public boolean showSeconds;    // Whether or not to show seconds
    }
}
// end::end[]