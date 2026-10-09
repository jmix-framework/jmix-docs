package com.company.demo.view.chat;

import com.company.demo.entity.ChatMessage;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.ai.provider.LLMProvider;
import com.vaadin.flow.router.Route;
import io.jmix.aichat.component.chat.AiChat;
import io.jmix.aichat.data.AiChatMessageContext;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

@Route(value = "chat", layout = MainView.class)
@ViewController(id = "ChatView")
@ViewDescriptor(path = "chat-view.xml")
public class ChatView extends StandardView {

    @ViewComponent
    private AiChat<ChatMessage> chat;
    @ViewComponent
    private CollectionContainer<ChatMessage> messagesDc;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    private ChatClient chatClient;

    @Subscribe
    public void onInit(final InitEvent event) {
        chatClient = chatClientBuilder.build();
    }

    // tag::system-prompt[]
    @Install(to = "chat", subject = "llmProvider")
    private Flux<String> llmProvider(final LLMProvider.LLMRequest request) {
        ChatClient.ChatClientRequestSpec prompt = chatClient.prompt();

        String systemPrompt = request.systemPrompt(); // <1>
        if (systemPrompt != null && !systemPrompt.isBlank()) {
            prompt = prompt.system(systemPrompt);
        }

        return prompt.user(request.userMessage())
                .stream()
                .content();
    }
    // end::system-prompt[]

    // tag::message-factory[]
    @Install(to = "chat", subject = "messageFactory")
    private ChatMessage messageFactory(final AiChatMessageContext context) {
        ChatMessage message = dataManager.create(ChatMessage.class);
        message.setRole(context.getRole());
        message.setContent(context.getContent());
        return message;
    }
    // end::message-factory[]

    @Subscribe("chat.clearAction")
    public void onClearAction(final ActionPerformedEvent event) {
        dataManager.remove(messagesDc.getItems());
        messagesDc.setItems(java.util.List.of());
    }
}
