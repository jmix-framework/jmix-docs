package com.company.onboarding.view.component.codeeditor;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "CodeEditorView", layout = MainView.class)
@ViewController("CodeEditorView")
@ViewDescriptor("code-editor-view.xml")
public class CodeEditorView extends StandardView {
}