package com.company.demo.view.attachments;

import com.company.demo.entity.ChatMessage;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.ai.common.AIAttachment;
import com.vaadin.flow.component.ai.provider.LLMProvider;
import com.vaadin.flow.router.Route;
import io.jmix.aichat.component.chat.AttachmentClickEvent;
import io.jmix.aichat.component.chat.AttachmentRejectedEvent;
import io.jmix.aichat.data.AiChatMessageContext;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.MessageBundle;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.ai.content.Media;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Route(value = "attachments", layout = MainView.class)
@ViewController(id = "AttachmentsView")
@ViewDescriptor(path = "attachments-view.xml")
public class AttachmentsView extends StandardView {

    @ViewComponent
    private MessageBundle messageBundle;

    @Autowired
    private ChatClient.Builder chatClientBuilder;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private FileStorageLocator fileStorageLocator;
    @Autowired
    private Downloader downloader;
    @Autowired
    private Notifications notifications;

    private ChatClient chatClient;

    @Subscribe
    public void onInit(final InitEvent event) {
        chatClient = chatClientBuilder.build();
    }

    // tag::llm-provider-attachments[]
    @Install(to = "chat", subject = "llmProvider")
    private Flux<String> llmProvider(final LLMProvider.LLMRequest request) {
        String userMessage = request.userMessage();
        if (userMessage == null || userMessage.isBlank()) { // <1>
            userMessage = "Describe the attached files.";
        }
        String text = userMessage;

        Media[] media = request.attachments().stream() // <2>
                .map(attachment -> Media.builder()
                        .name(attachment.name())
                        .mimeType(MimeTypeUtils.parseMimeType(attachment.mimeType()))
                        .data(new ByteArrayResource(attachment.data()))
                        .build())
                .toArray(Media[]::new);

        return chatClient.prompt()
                .user(spec -> spec.text(text).media(media))
                .stream()
                .content();
    }
    // end::llm-provider-attachments[]

    // tag::attachment-factory[]
    @Install(to = "chat", subject = "messageFactory")
    private ChatMessage messageFactory(final AiChatMessageContext context) {
        ChatMessage message = dataManager.create(ChatMessage.class);
        message.setRole(context.getRole());
        message.setContent(context.getContent());

        FileStorage fileStorage = fileStorageLocator.getDefault();
        List<FileRef> refs = new ArrayList<>();
        for (AIAttachment attachment : context.getAttachments()) {
            refs.add(fileStorage.saveStream(attachment.name(),
                    new ByteArrayInputStream(attachment.data())));
        }
        message.setAttachments(refs);

        return message;
    }
    // end::attachment-factory[]

    // tag::rejected[]
    @Subscribe("chat")
    public void onChatAttachmentRejected(
            final AttachmentRejectedEvent<ChatMessage> event) {
        notifications.show(messageBundle.formatMessage("attachmentRejected.notification",
                event.getFileName(), event.getReason().name()));
    }
    // end::rejected[]

    // tag::download[]
    @Subscribe("chat")
    public void onChatAttachmentClick(
            final AttachmentClickEvent<ChatMessage> event) {
        FileRef fileRef = event.getFileRef();
        if (fileRef != null) {
            downloader.download(fileRef);
        }
    }
    // end::download[]
}
