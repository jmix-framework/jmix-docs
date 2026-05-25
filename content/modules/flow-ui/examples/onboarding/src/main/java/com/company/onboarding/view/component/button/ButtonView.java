package com.company.onboarding.view.component.button;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "ButtonView", layout = MainView.class)
@ViewController("ButtonView")
@ViewDescriptor("button-view.xml")
public class ButtonView extends StandardView {
    // tag::lumoBtn[]
    @ViewComponent
    protected JmixButton lumoBtn;

    // end::lumoBtn[]
    // tag::vaadinBtn[]
    @ViewComponent
    protected JmixButton vaadinBtn;

    // end::vaadinBtn[]
    @Autowired
    private Notifications notifications;
    // tag::basics-1[]
    @Subscribe(id = "toolsButton", subject = "clickListener")
    public void onToolsButtonClick(final ClickEvent<JmixButton> event) {
        // ...
    }
    // end::basics-1[]

    // tag::clickEvent[]
    @Subscribe("helloButton") // <1>
    public void onHelloButtonClick(ClickEvent<Button> event) {
        Button button = event.getSource(); // <2>
        // ...
    }
    // end::clickEvent[]

    @Subscribe("helloButton")
    public void onHelloButtonAttach(AttachEvent event) {
        
    }

    @Subscribe("helloButton")
    public void onHelloButtonDetach(DetachEvent event) {

    }

    @Subscribe("helloButton")
    public void onHelloButtonBlur(BlurNotifier.BlurEvent<Button> event) {

    }

    @Subscribe("helloButton")
    public void onHelloButtonFocus(FocusNotifier.FocusEvent<Button> event) {

    }

    @ViewComponent
    private JmixButton helloButton;

    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        helloButton.setText("aaa");
        helloButton.setTitle("aaaaaa");
        // tag::lumoBtn[]
        Icon lumoIcon = new Icon("lumo", "photo");
        lumoBtn.setIcon(lumoIcon);
        // end::lumoBtn[]
        // tag::vaadinBtn[]
        Icon vaadinIcon = new Icon(VaadinIcon.PHONE);
        vaadinBtn.setIcon(vaadinIcon);
        // end::vaadinBtn[]
        // tag::onInit[]
    }

    // end::onInit[]
    // tag::clickListener[]
    @Subscribe(id = "clickBtn", subject = "clickListener") // <1>
    public void onClickBtnClick(final ClickEvent<JmixButton> event) {
        JmixButton button = event.getSource(); // <2>
    }
    // end::clickListener[]
    // tag::doubleClickListener[]
    @Subscribe(id = "clickBtn", subject = "doubleClickListener")
    public void onClickBtnClick1(final ClickEvent<JmixButton> event) {
        notifications.show("This is doubleClickListener");
    }
    // end::doubleClickListener[]
    // tag::singleClickListener[]
    @Subscribe(id = "clickBtn", subject = "singleClickListener")
    public void onClickBtnClick2(final ClickEvent<JmixButton> event) {
        notifications.show("This is singleClickListener");
    }
    // end::singleClickListener[]
}