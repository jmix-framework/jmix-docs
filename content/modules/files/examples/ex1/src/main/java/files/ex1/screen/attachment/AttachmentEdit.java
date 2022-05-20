package files.ex1.screen.attachment;

import files.ex1.entity.Attachment;
import io.jmix.core.FileRef;
import io.jmix.ui.download.DownloadFormat;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@UiController("Attachment.edit")
@UiDescriptor("attachment-edit.xml")
@EditedEntityContainer("attachmentDc")
public class AttachmentEdit extends StandardEditor<Attachment> {

    // tag::downloader[]
    @Autowired
    private Downloader downloader;

    // end::downloader[]

    // tag::download-from-fs[]
    private void downloadFromFileStorage(Attachment attachment) {
        FileRef fileRef = attachment.getFile();
        downloader.download(fileRef);
    }
    // end::download-from-fs[]

    // tag::download-array[]
    private void downloadByteArray(byte[] content) {
        downloader.download(content, "notes.txt", DownloadFormat.TEXT);
    }
    // end::download-array[]

    // tag::download-format[]
    private void downloadByteArrayInCustomFormat(byte[] content) {
        DownloadFormat format = new DownloadFormat("application/data", "dat");
        downloader.download(content, "some-file.dat", format);
    }
    // end::download-format[]

    // tag::data-provider[]
    private void downloadInputStreamContent(InputStream inputStream) {
        downloader.download(() -> inputStream, "archive.zip");
    }
    // end::data-provider[]
}