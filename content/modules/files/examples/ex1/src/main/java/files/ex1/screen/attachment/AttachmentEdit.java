package files.ex1.screen.attachment;

import files.ex1.entity.Attachment;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Attachment.edit")
@UiDescriptor("attachment-edit.xml")
@EditedEntityContainer("attachmentDc")
public class AttachmentEdit extends StandardEditor<Attachment> {
}