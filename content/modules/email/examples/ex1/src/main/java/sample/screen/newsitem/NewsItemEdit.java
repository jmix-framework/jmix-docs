package sample.screen.newsitem;

import io.jmix.core.Resources;
import io.jmix.email.*;
import io.jmix.ui.Dialogs;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import sample.entity.NewsItem;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

// tag::news-item-edit1[]
@UiController("sample_NewsItem.edit")
@UiDescriptor("news-item-edit.xml")
@EditedEntityContainer("newsItemDc")
public class NewsItemEdit extends StandardEditor<NewsItem> {

    // tag::just-created-1[]
    private boolean justCreated;
    // end::just-created-1[]
    @Autowired
    private Emailer emailer;

    @Inject
    protected Dialogs dialogs;

    @Autowired
    protected Resources resources;

    // tag::just-created-2[]

    @Subscribe
    public void onInitEntity(InitEntityEvent<NewsItem> event) {
        justCreated = true;
    }
    // end::just-created-2[]

    // tag::post-commit-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) {
        if (justCreated) { // <1>
            dialogs.createOptionDialog() // <2>
                    .withCaption("Email")
                    .withMessage("Send the news item by email?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES) {
                                @Override
                                public void actionPerform(Component component) {
                                    try {
                                        sendByEmail(); // <3>
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
    // end::post-commit-event[]

    private EmailAttachment createEmailAttachment(String pathToResources) throws IOException {
        InputStream resourceAsStream = resources.getResourceAsStream(pathToResources);
        byte[] bytes = IOUtils.toByteArray(resourceAsStream);
        // tag::create-attachment[]
        EmailAttachment emailAtt = new EmailAttachment(bytes, "logo.png", "logoId");
        // end::create-attachment[]
        return emailAtt;
    }


    private void sendWithAttachment() throws EmailException {
        // tag::text-attachment[]
        String att = "<html><body><h1>Content of attachment</h1></body></html>";
        EmailAttachment emailAtt = EmailAttachment.createTextAttachment(att, StandardCharsets.UTF_8.name(), "attachment.html");
        // end::text-attachment[]
        // tag::email-info[]
        EmailInfo emailInfo = EmailInfoBuilder.create("john.doe@company.com",
                "Email subject", "Email body")
                .build();
        emailer.sendEmail(emailInfo);
        // end::email-info[]
    }


    // tag::send-by-email[]
    private void sendByEmail() throws IOException {
        InputStream resourceAsStream = resources.getResourceAsStream("sample/logo.png");
        byte[] bytes = IOUtils.toByteArray(resourceAsStream); // <1>
        EmailAttachment emailAtt = new EmailAttachment(bytes,
                "logo.png", "logoId"); // <2>
        NewsItem newsItem = getEditedEntity();
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses("john.doe@company.com,jane.roe@company.com") // <3>
                .setSubject(newsItem.getCaption()) // <4>
                .setFrom(null) // <5>
                .setBody(newsItem.getContent())
                .setAttachments(emailAtt) // <6>
                .build();
        emailer.sendEmailAsync(emailInfo);
    }
    // end::send-by-email[]
}
