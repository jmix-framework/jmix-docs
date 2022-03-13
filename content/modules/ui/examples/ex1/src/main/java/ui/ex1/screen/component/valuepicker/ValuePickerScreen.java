package ui.ex1.screen.component.valuepicker;

import com.google.common.base.Strings;
import io.jmix.ui.Actions;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.valuepicker.ValueClearAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.component.ValuePicker;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@UiController("valuePicker-screen")
@UiDescriptor("valuePicker-screen.xml")
public class ValuePickerScreen extends Screen {
    // tag::integer-value-picker[]
    @Autowired
    private ValuePicker<Integer> ageValuePicker;

    // end::integer-value-picker[]
    // tag::inject-notifications[]
    @Autowired
    protected Notifications notifications;
    // end::inject-notifications[]
    @Autowired
    protected ValuePicker<String> valuePicker;
    // tag::inject-uiComponents[]
    @Autowired
    private UiComponents uiComponents;
    // end::inject-uiComponents[]
    // tag::inject-messageBundle[]
    @Autowired
    private MessageBundle messageBundle;
    // end::inject-messageBundle[]
    // tag::inject-vBox[]
    @Autowired
    private VBoxLayout vBox;
    // end::inject-vBox[]
    // tag::add-clear-action[]
    @Autowired
    protected ValuePicker<String> loginValuePicker;
    @Autowired
    protected Actions actions;

    // end::add-clear-action[]
    @Autowired
    private Dialogs dialogs;
    // tag::inject-valuePickerClearAction[]
    @Named("valuePicker.clear")
    private ValueClearAction valuePickerClear;

    // end::inject-valuePickerClearAction[]
    // tag::onInit-start[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::onInit-start[]
        // tag::base-action-value-picker[]
        ValuePicker valueField = uiComponents.create(ValuePicker.NAME);

        valueField.addAction(new BaseAction("hello") {
            @Override
            public String getCaption() {
                return null;
            }

            @Override
            public String getDescription() {
                return messageBundle.getMessage("helloDescription");
            }

            @Override
            public String getIcon() {
                return JmixIcon.HANDSHAKE_O.source();
            }

            @Override
            public void actionPerform(Component component) {
                notifications.create()
                        .withCaption("Hello!")
                        .withType(Notifications.NotificationType.TRAY)
                        .show();
            }
        });
        valueField.addAction(new BaseAction("goodbye")
                .withCaption(null)
                .withDescription(messageBundle.getMessage("goodbyeDescription"))
                .withIcon(JmixIcon.HAND_PAPER_O.source())
                .withHandler(e ->
                        notifications.create()
                                .withCaption("Goodbye!")
                                .withType(Notifications.NotificationType.TRAY)
                                .show()));
        vBox.add(valueField);
        // end::base-action-value-picker[]
        loginValuePicker.addAction(new BaseAction("showValue")
                .withHandler(actionPerformedEvent -> {
                    String value = loginValuePicker.getValue();

                    notifications.create()
                            .withCaption(value != null ? value : "No value")
                            .show();
                })
                .withDescription("Show Value")
                .withIcon(JmixIcon.EYE.source()));
    // tag::add-clear-action1[]
        loginValuePicker.addAction(actions.create(ValueClearAction.ID));
        // end::add-clear-action1[]
        // tag::onInit-end[]
    }
    // end::onInit-end[]


    @Subscribe("valuePicker.generate")
    protected void onValuePickerGenerate(Action.ActionPerformedEvent event) {
        valuePicker.setValue(RandomStringUtils.randomAlphabetic(5, 10));
    }
    // tag::custom-action-handler[]
    @Subscribe("loginValuePicker.generate") // <1>
    protected void onLoginValuePickerGenerate(Action.ActionPerformedEvent event) {
        loginValuePicker.setValue(RandomStringUtils.randomAlphabetic(5, 10));
    }
    // end::custom-action-handler[]

    // tag::field-value-change-event[]
    @Subscribe("ageValuePicker")
    public void onAgeValuePickerFieldValueChange(ValuePicker.FieldValueChangeEvent<Integer> event) {
        String text = event.getText(); // <1>
        notifications.create()
                .withCaption("Entered value: " + text)
                .show();
        if (!Strings.isNullOrEmpty(text)) {
            ageValuePicker.setValue(Integer.parseInt(text)); // <2>
        }
    }

    // end::field-value-change-event[]
    // tag::clear-action-performed-event[]
    @Subscribe("valuePicker.clear")
    public void onValuePickerClear(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to clear the field?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> valuePickerClear.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::clear-action-performed-event[]
}
