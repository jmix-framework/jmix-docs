package com.company.demo.view.document;

import com.company.demo.entity.Document;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "documents/:id", layout = MainView.class)
@ViewController("Document.detail")
@ViewDescriptor("document-detail-view.xml")
@EditedEntityContainer("documentDc")
public class DocumentDetailView extends StandardDetailView<Document> {

    //tag::sequences-inject[]
    @Autowired
    private Sequences sequences;
    //end::sequences-inject[]

    //tag::sequences-use[]
    @Autowired
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