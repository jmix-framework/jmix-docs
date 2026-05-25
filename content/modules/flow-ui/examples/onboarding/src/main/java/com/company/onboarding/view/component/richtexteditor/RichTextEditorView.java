package com.company.onboarding.view.component.richtexteditor;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.richtexteditor.RichTextEditor;
import io.jmix.flowui.view.*;

@Route(value = "rich-text-editor-view", layout = MainView.class)
@ViewController("RichTextEditorView")
@ViewDescriptor("rich-text-editor-view.xml")
public class RichTextEditorView extends StandardView {
    //tag::richtexteditor[]
    @ViewComponent
    private RichTextEditor richTextEditor;
    //end::richtexteditor[]

    //tag::oninit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        //end::oninit[]
        //tag::html[]
        richTextEditor.setValue("<i>Jackdaws </i><u>love</u> " +
                "my <b>big</b> <sup>sphinx</sup> of " +
                "<span style=\"background-color:red;\">quartz</span>");
        //end::html[]
        //tag::oninit[]
    }
    //end::oninit[]
}