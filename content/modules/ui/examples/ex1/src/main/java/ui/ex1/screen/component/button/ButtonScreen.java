package ui.ex1.screen.component.button;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.icon.Icons;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.screen.*;
import io.jmix.ui.theme.ThemeClassNames;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.icon.FontAwesome5Icon;
import ui.ex1.icon.MyIcon;

@UiController("button-screen")
@UiDescriptor("button-screen.xml")
// tag::route[]
@Route("button-screen")
// end::route[]
// tag::button-screen-start[]
public class ButtonScreen extends Screen {
    // end::button-screen-start[]
    @Autowired
    protected Notifications notifications;

    @Autowired
    private Button iconButton;
    @Autowired
    private Button dependentBtn;
    @Autowired
    private Button cIconBtn;
    @Autowired
    private Button oIconBtn;
    @Autowired
    private Button okIconBtn;
    // tag::inject-icons[]
    @Autowired
    private Icons icons;

    // end::inject-icons[]

    // tag::click-handler[]
    @Subscribe("helloButton") // <1>
    protected void onHelloButtonClick(Button.ClickEvent event) {
        Button button = event.getSource(); // <2>
        // ...
    }
    // end::click-handler[]

    @Autowired
    protected Button styledBtn1;

    @Subscribe("saveButton")
    protected void onSaveButtonClick(Button.ClickEvent event) {
        save(event.getSource().getId());
    }

    @Subscribe("saveButton2")
    protected void onSaveButton2Click(Button.ClickEvent event) {
        save(event.getSource().getId());
    }

    public void save(String id) {
        notifications.create()
                .withCaption("Save called from " + id)
                .show();
    }

    @Subscribe("mainBtn")
    public void onMainBtnClick(Button.ClickEvent event) {
        dependentBtn.click();
    }

    @Subscribe("dependentBtn")
    public void onDependentBtnClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("This event was sent by the Dependent button")
                .show();
    }
    // tag::url-params-changed-event[]
    @Subscribe
    protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
        //...
    }
    // end::url-params-changed-event[]

    // tag::init-start[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::init-start[]
        // tag::set-style-name[]
        styledBtn1.setStyleName(ThemeClassNames.BUTTON_BORDERLESS);
        // end::set-style-name[]
        // tag::set-icon-from-set[]
        iconButton.setIconFromSet(MyIcon.PENGUIN);
        // end::set-icon-from-set[]
        // tag::set-custom-icon[]
        cIconBtn.setIconFromSet(FontAwesome5Icon.JAVA);
        // end::set-custom-icon[]
        // tag::set-icon-source[]
        oIconBtn.setIcon(JmixIcon.OK.source());
        // end::set-icon-source[]
        // tag::set-icon[]
        okIconBtn.setIcon(icons.get(JmixIcon.OK));
        // end::set-icon[]
        // tag::init-end[]
    }
    // end::init-end[]

    // tag::button-screen-end[]
}
// end::button-screen-end[]
