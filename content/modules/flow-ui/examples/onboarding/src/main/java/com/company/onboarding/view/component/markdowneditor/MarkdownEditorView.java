package com.company.onboarding.view.component.markdowneditor;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "markdown-editor-view", layout = MainView.class)
@ViewController(id = "MarkdownEditorView")
@ViewDescriptor(path = "markdown-editor-view.xml")
public class MarkdownEditorView extends StandardView {
}