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

    private boolean justCreated; // <1>

    @Autowired
    private Emailer emailer;

    @Inject
    protected Dialogs dialogs;

    @Autowired
    protected Resources resources;

    @Subscribe
    public void onInitEntity(InitEntityEvent<NewsItem> event) { // <2>
        justCreated = true;
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) { // <3>
        if (justCreated) {
            dialogs.createOptionDialog() // <4>
                    .withCaption("Email")
                    .withMessage("Send the news item by email?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES) {
                                @Override
                                public void actionPerform(Component component) {
                                    try {
                                        sendByEmail();
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
    // end::news-item-edit1[]

    // tag::text-attachment[]
    private void sendWithAttachment() {
        String att = "<html><body><h1>Content of attachment</h1></body></html>";
        EmailAttachment emailAtt = EmailAttachment.createTextAttachment(att, StandardCharsets.UTF_8.name(), "attachment.html");
        EmailInfo emailInfo = EmailInfoBuilder.create("john.doe@company.com", "Email with attachment", "Email body")
                .setAttachments(emailAtt)
                .build();
        emailer.sendEmailAsync(emailInfo);
    }
    // end::text-attachment[]

    // tag::news-item-edit2[]

    private void sendByEmail() throws IOException { // <5>
        InputStream resourceAsStream = resources.getResourceAsStream("/sample/logo.png");
        byte[] bytes = IOUtils.toByteArray(resourceAsStream); // <6>
        // tag::file-attachment[]
        EmailAttachment emailAtt = new EmailAttachment(bytes,
                "logo.png", "logoId"); // <7>
        // end::file-attachment[]
        NewsItem newsItem = getEditedEntity();
        EmailInfo emailInfo = EmailInfoBuilder.create()
                .setAddresses("john.doe@company.com,jane.roe@company.com") // <8>
                .setSubject(newsItem.getCaption()) // <9>
                .setFrom(null) // <10>
                .setBody(newsItem.getContent())
                .setAttachments(emailAtt) // <11>
                .build();
        emailer.sendEmailAsync(emailInfo);
    }
}
// end::news-item-edit2[]