package bpm.ex1.screen.forms;

import io.jmix.bpmui.processform.ProcessFormContext;
import io.jmix.bpmui.processform.annotation.ProcessForm;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.flowable.engine.FormService;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("smpl_StartProcessTest")
@UiDescriptor("start-process-test.xml")
@ProcessForm
public class StartProcessTest extends Screen {

    @Autowired
    private ProcessFormContext processFormContext;

    @Subscribe("startProcessBtn")
    public void onStartProcessBtnClick(Button.ClickEvent event) {
        processFormContext.processStarting()
                .withBusinessKey("order")
                .saveInjectedProcessVariables()
                .start();
        closeWithDefaultAction();
    }
}