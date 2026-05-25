package com.company.demo.view.attachment;

import com.company.demo.entity.Attachment;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

@Route(value = "attachments/:id", layout = MainView.class)
@ViewController("Attachment.detail")
@ViewDescriptor("attachment-detail-view.xml")
@EditedEntityContainer("attachmentDc")
public class AttachmentDetailView extends StandardDetailView<Attachment> {

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