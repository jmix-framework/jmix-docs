package files.ex1.screen.attachment;

import files.ex1.entity.Attachment;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.component.Table;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Attachment.browse")
@UiDescriptor("attachment-browse.xml")
@LookupComponent("attachmentsTable")
public class AttachmentBrowse extends StandardLookup<Attachment> {

    // tag::files[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Downloader downloader; // <1>

    @Install(to = "attachmentsTable.fileName", subject = "columnGenerator")
    private Component attachmentsTableFileColumnGenerator(Attachment attachment) {
        if (attachment.getFile() != null) {
            LinkButton linkButton = uiComponents.create(LinkButton.class);
            linkButton.setAction(new BaseAction("download")
                    .withCaption(attachment.getFile().getFileName())
                    .withHandler(actionPerformedEvent ->
                            downloader.download(attachment.getFile()) // <2>
                    )
            );
            return linkButton;
        } else {
            return new Table.PlainTextCell("<empty>");
        }
    }
    // end::files[]
}