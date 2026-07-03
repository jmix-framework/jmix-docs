package com.company.demo.view.document;

import com.company.demo.entity.Document;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.Resources;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

@Route(value = "documents/:id", layout = MainView.class)
@ViewController("Document.detail")
@ViewDescriptor("document-detail-view.xml")
@EditedEntityContainer("documentDc")
public class DocumentDetailView extends StandardDetailView<Document> {

    protected static final String SRC_PATH = "META-INF/resources/html/html-file.html";
    //tag::resources-example[]
    @Autowired
    private Resources resources;

    @Subscribe
    public void onInit(final InitEvent event) {
        try (InputStream stream = resources.getResourceAsStream(SRC_PATH)) {
            if (stream == null) {
                // resource not found
                return;
            }
            // use the stream
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //end::resources-example[]
    //tag::sequences-inject[]
    @Autowired
    private Sequences sequences;
    //end::sequences-inject[]

    //tag::sequences-use[]
    @ViewComponent
    private InstanceContainer<Document> documentDc;

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(final DataContext.PreSaveEvent event) {
        Long number = sequences.createNextValue(Sequence.withName("document_number") // <1>
                .setStore("additional") // <2>
                .setStartValue(10) // <3>
                .setIncrement(10)); // <4>
        documentDc.getItem().setNumber(number);
    }
    //end::sequences-use[]

    private void generateUniqueNumber() {
        //tag::sequences-simple[]
        Long nextNumber = sequences.createNextValue(Sequence.withName("some_seq"));
        //end::sequences-simple[]
    }
}