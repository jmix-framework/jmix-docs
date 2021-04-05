package bpm.ex1.screen.forms;

import bpm.ex1.entity.User;
import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.Param;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.bpmui.processform.annotation.ProcessFormParam;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("smpl_ActorSelectionForm")
@UiDescriptor("actor-selection-form.xml")
// tag::actor-form[]
// tag::params[]
@ProcessForm(
        params = {
                @Param(name = "variableName"),
                @Param(name = "entityPickerCaption")
        }
)
// end::params[]
public class ActorSelectionForm extends Screen {

    @Autowired
    private ProcessFormContext processFormContext;

    @Autowired
    private EntityPicker<String> userEntityPicker;

    // tag::params-annotation[]
    @Autowired
    @ProcessFormParam
    private String variableName;

    @Autowired
    @ProcessFormParam
    private String entityPickerCaption;
    // end::params-annotation[]

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        userEntityPicker.setCaption(entityPickerCaption);
    }

    @Subscribe("completeTaskBtn")
    private void onCompleteTaskBtnClick(Button.ClickEvent event) {
        processFormContext.taskCompletion()
                .addProcessVariable(variableName, userEntityPicker.getValue())
                .complete();
        closeWithDefaultAction();
    }
}
// end::actor-form[]