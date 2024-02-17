package com.company.demo.view.document;

import com.company.demo.entity.Document;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "documents", layout = MainView.class)
@ViewController("Document.list")
@ViewDescriptor("document-list-view.xml")
@LookupComponent("documentsDataGrid")
@DialogMode(width = "64em")
public class DocumentListView extends StandardListView<Document> {
}