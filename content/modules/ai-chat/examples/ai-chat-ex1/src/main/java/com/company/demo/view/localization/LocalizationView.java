package com.company.demo.view.localization;

import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.ai.provider.LLMProvider;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@Route(value = "localization", layout = MainView.class)
@ViewController(id = "LocalizationView")
@ViewDescriptor(path = "localization-view.xml")
public class LocalizationView extends StandardView {

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
}
