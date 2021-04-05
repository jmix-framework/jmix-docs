package bpm.ex1.screen.forms;

import bpm.ex1.service.AcceptsProcessDefinitionData;
import io.jmix.bpm.entity.ProcessDefinitionData;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom[]
@UiController("smpl_CustomStartForm")
@UiDescriptor("custom-start-form.xml")
public class CustomStartForm extends Screen implements AcceptsProcessDefinitionData {

    private ProcessDefinitionData processDefinitionData;

    @Autowired
    private RuntimeService runtimeService;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        runtimeService.startProcessInstanceById(processDefinitionData.getId());
        closeWithDefaultAction();
    }

    @Override
    public void setProcessDefinitionData(ProcessDefinitionData processDefinitionData) {
        this.processDefinitionData = processDefinitionData;
    }
}
// end::custom[]
