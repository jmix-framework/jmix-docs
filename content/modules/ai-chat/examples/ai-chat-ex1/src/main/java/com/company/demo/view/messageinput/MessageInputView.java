package com.company.demo.view.messageinput;

import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.BlurNotifier;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.FocusNotifier;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.aichat.component.messageinput.AiMessageInput;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "message-input", layout = MainView.class)
@ViewController(id = "MessageInputView")
@ViewDescriptor(path = "message-input-view.xml")
public class MessageInputView extends StandardView {

    @ViewComponent
    private Span lastMessageSpan;
    @ViewComponent
    private Span focusStatusSpan;
    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private Notifications notifications;

    // tag::submit[]
    @Subscribe("basicsComposer")
    public void onBasicsComposerSubmit(final AiMessageInput.SubmitEvent event) {
        lastMessageSpan.setText(event.getValue());
    }
    // end::submit[]

    // tag::slot-injection[]
    @ViewComponent
    private JmixButton promptLibraryButton;

    @Subscribe
    public void onInit(final InitEvent event) {
        promptLibraryButton.addClickListener(clickEvent ->
                notifications.show(messageBundle.getMessage("promptLibraryButton.notification")));
    }
    // end::slot-injection[]

    @Subscribe("clearButton")
    public void onClearButtonClick(final ClickEvent<JmixButton> event) {
        lastMessageSpan.setText("");
    }

    // tag::focus-events[]
    @Subscribe("focusComposer")
    public void onFocusComposerFocus(final FocusNotifier.FocusEvent<AiMessageInput> event) {
        focusStatusSpan.setText(messageBundle.getMessage("focusStatus.focused"));
    }

    @Subscribe("focusComposer")
    public void onFocusComposerBlur(final BlurNotifier.BlurEvent<AiMessageInput> event) {
        focusStatusSpan.setText(messageBundle.getMessage("focusStatus.blurred"));
    }
    // end::focus-events[]
}
