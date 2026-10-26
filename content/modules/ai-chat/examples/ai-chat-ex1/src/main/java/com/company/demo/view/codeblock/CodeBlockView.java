package com.company.demo.view.codeblock;

import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.aichat.component.codeblock.AiCodeBlock;
import io.jmix.aichat.kit.component.codeblock.AiCodeBlockLanguage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "code-block", layout = MainView.class)
@ViewController(id = "CodeBlockView")
@ViewDescriptor(path = "code-block-view.xml")
public class CodeBlockView extends StandardView {

    @ViewComponent
    private VerticalLayout programmaticBox;

    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::programmatic[]
        AiCodeBlock codeBlock = uiComponents.create(AiCodeBlock.class);
        codeBlock.setLanguage(AiCodeBlockLanguage.JAVA);
        codeBlock.setCode("""
                @Autowired
                private Notifications notifications;

                @Subscribe("saveButton")
                public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
                    notifications.show("Saved");
                }""");

        programmaticBox.add(codeBlock);
        // end::programmatic[]
    }
}
