package ui.ex1.screen.actions;

import io.jmix.core.MetadataTools;
import io.jmix.core.common.event.Subscription;
import io.jmix.email.EmailAttachment;
import io.jmix.email.EmailInfo;
import io.jmix.email.EmailInfoBuilder;
import io.jmix.email.Emailer;
import io.jmix.ui.action.ActionType;
import io.jmix.ui.action.ItemTrackingAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.meta.StudioAction;
import io.jmix.ui.meta.StudioPropertiesItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

// tag::send-by-email-action[]
@StudioAction(target = "io.jmix.ui.component.ListComponent", description = "Sends selected entity by email")// <1>
@ActionType("sendByEmail")// <2>
public class SendByEmailAction <E> extends ItemTrackingAction {// <3>

    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private Emailer emailer;


    private String recipientAddress = "admin@example.com";

    private Function<E, String> bodyGenerator;
    private Function<E, List<EmailAttachment>> attachmentProvider;

    public SendByEmailAction(String id) {
        super(id);
        setCaption("Send by email");
    }

    @StudioPropertiesItem(required = true, defaultValue = "admin@example.com")// <4>
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Subscription addEmailSentListener(Consumer<EmailSentEvent> listener) {// <5>
        return getEventHub().subscribe(EmailSentEvent.class, listener);
    }

    public void setBodyGenerator(Function<E, String> bodyGenerator) {// <6>
        this.bodyGenerator = bodyGenerator;
    }

    public void setAttachmentProvider(Function<E, List<EmailAttachment>> attachmentProvider) {// <7>
        this.attachmentProvider = attachmentProvider;
    }

    @Override
    public void actionPerform(Component component) {
        if (recipientAddress == null || bodyGenerator == null) {
            throw new IllegalStateException("Required parameters are not set");
        }

        E selected = (E) getTarget().getSingleSelected();
        if (selected == null) {
            return;
        }

        String caption = "Entity " + metadataTools.getInstanceName(selected) + " info";
        String body = bodyGenerator.apply(selected);// <8>
        List<EmailAttachment> attachments = attachmentProvider != null ? attachmentProvider.apply(selected)// <9>
                : new ArrayList<>();

        EmailInfo info = EmailInfoBuilder.create()
                .setAddresses(recipientAddress)
                .setSubject(caption)
                .setBody(body)
                .setBodyContentType(EmailInfo.TEXT_CONTENT_TYPE)
                .setAttachments(attachments.toArray(new EmailAttachment[0]))
                .build();

        emailer.sendEmailAsync(info);// <10>

        EmailSentEvent event = new EmailSentEvent(this, info);
        eventHub.publish(EmailSentEvent.class, event);// <11>
    }

    public static class EmailSentEvent extends EventObject {// <12>
        private final EmailInfo emailInfo;

        public EmailSentEvent(SendByEmailAction origin, EmailInfo emailInfo) {
            super(origin);
            this.emailInfo = emailInfo;
        }

        public EmailInfo getEmailInfo() {
            return emailInfo;
        }
    }
}
// end::send-by-email-action[]