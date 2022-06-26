package bpm.ex1.service;

import io.jmix.bpmui.processform.screencreator.ProcessFormScreenCreator;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.screen.OpenMode;
import io.jmix.ui.screen.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// tag::custom[]
@Component("samples_MyCustomProcessFormScreenCreator")
@Order(1) // <1>
public class MyCustomProcessFormScreenCreator implements ProcessFormScreenCreator {

    @Autowired
    private ScreenBuilders screenBuilders;

    @Override
    public String isApplicableFor() { // <2>
        return "custom";
    }

    @Override
    public Screen createStartProcessScreen(CreationContext creationContext) { // <3>
        Screen screen = screenBuilders.screen(creationContext.getFrameOwner())
                .withScreenId(creationContext.getFormData().getScreenId())
                .withOpenMode(OpenMode.DIALOG)
                .build();
        if (screen instanceof AcceptsProcessDefinition) { // <4>
            ((AcceptsProcessDefinition) screen)
                    .setProcessDefinition(creationContext.getProcessDefinition());
        }
        return screen;
    }

    @Override
    public Screen createUserTaskScreen(CreationContext creationContext) { //<5>
        Screen screen = screenBuilders.screen(creationContext.getFrameOwner())
                .withScreenId(creationContext.getFormData().getScreenId())
                .withOpenMode(OpenMode.DIALOG)
                .build();
        if (screen instanceof AcceptsTask) { // <6>
            ((AcceptsTask) screen).setTask(creationContext.getTask());
        }
        return screen;
    }
}
// end::custom[]