package bpm.ex1.screen.forms;

import bpm.ex1.service.AcceptsProcessDefinition;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom[]
@UiController("smpl_CustomStartForm")
@UiDescriptor("custom-start-form.xml")
public class CustomStartForm extends Screen implements AcceptsProcessDefinition {

    private ProcessDefinition processDefinition;

    @Autowired
    private RuntimeService runtimeService;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        runtimeService.startProcessInstanceById(processDefinition.getId());
        closeWithDefaultAction();
    }

    @Override
    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }
}
// end::custom[]
