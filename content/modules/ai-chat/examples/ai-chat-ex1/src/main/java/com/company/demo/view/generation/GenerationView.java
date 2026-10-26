package com.company.demo.view.generation;

import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ai.provider.LLMProvider;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.aichat.component.chat.AiChat;
import io.jmix.aichat.component.chat.GenerationFailedEvent;
import io.jmix.aichat.component.chat.GenerationStateChangeEvent;
import io.jmix.aichat.component.messagelist.GenerationState;
import io.jmix.aichat.data.SimpleAiChatMessage;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.flowui.view.Install;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@Route(value = "generation", layout = MainView.class)
@ViewController(id = "GenerationView")
@ViewDescriptor(path = "generation-view.xml")
public class GenerationView extends StandardView {

    @ViewComponent
    private AiChat<SimpleAiChatMessage> chat;
    @ViewComponent
    private JmixButton stopButton;
    @ViewComponent
    private Span stateSpan;
    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private Notifications notifications;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private ChatClient chatClient;

    @Subscribe
    public void onInit(final InitEvent event) {
        chatClient = chatClientBuilder.build();
    }

    @Install(to = "chat", subject = "llmProvider")
    private Flux<String> llmProvider(final LLMProvider.LLMRequest request) {
        return chatClient.prompt()
                .user(request.userMessage())
                .stream()
                .content();
    }

    // tag::state-listener[]
    @Subscribe("chat")
    public void onChatGenerationStateChange(
            final GenerationStateChangeEvent<SimpleAiChatMessage> event) {
        stateSpan.setText(event.getNewState().name());
        stopButton.setEnabled(event.getNewState() == GenerationState.GENERATING);
    }
    // end::state-listener[]

    // tag::regenerate[]
    @Subscribe("stopButton")
    public void onStopButtonClick(final ClickEvent<JmixButton> event) {
        chat.stop();
    }

    @Subscribe("regenerateButton")
    public void onRegenerateButtonClick(final ClickEvent<JmixButton> event) {
        chat.regenerate();
    }
    // end::regenerate[]

    // tag::failed-listener[]
    @Subscribe("chat")
    public void onChatGenerationFailed(
            final GenerationFailedEvent<SimpleAiChatMessage> event) {
        notifications.show(messageBundle.formatMessage("generationFailed.notification",
                event.getError().getMessage()));
    }
    // end::failed-listener[]
}
