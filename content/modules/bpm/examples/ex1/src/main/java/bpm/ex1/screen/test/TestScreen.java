package bpm.ex1.screen.test;

import bpm.ex1.service.MyCustomBean;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_TestScreen")
@UiDescriptor("test-screen.xml")
public class TestScreen extends Screen {

    @Autowired
    private MyCustomBean myCustomBean;

    @Subscribe("testListsBtn")
    public void onTestListsBtnClick(Button.ClickEvent event) {
        myCustomBean.showMethods();
    }

}