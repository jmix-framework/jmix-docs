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
    public String isApplicableFor() { // <3>
        return "custom";
    } // <2>

    @Override
    public Screen createStartProcessScreen(CreationContext creationContext) { // <3>
        Screen screen = screenBuilders.screen(creationContext.getFrameOwner())
                .withScreenId(creationContext.getFormData().getScreenId())
                .withOpenMode(OpenMode.DIALOG)
                .build();
        if (screen instanceof AcceptsProcessDefinitionData) { // <4>
            ((AcceptsProcessDefinitionData) screen)
                    .setProcessDefinitionData(creationContext.getProcessDefinitionData());
        }
        return screen;
    }

    @Override
    public Screen createUserTaskScreen(CreationContext creationContext) { //<5>
        Screen screen = screenBuilders.screen(creationContext.getFrameOwner())
                .withScreenId(creationContext.getFormData().getScreenId())
                .withOpenMode(OpenMode.DIALOG)
                .build();
        if (screen instanceof AcceptsTaskData) { // <6>
            ((AcceptsTaskData) screen).setTaskData(creationContext.getTaskData());
        }
        return screen;
    }
}
// end::custom[]