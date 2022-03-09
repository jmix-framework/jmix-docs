package ui.ex1.screen.screens.open;

import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

// tag::new-other-screen[]
@UiController("sample_NewOtherScreen")
@UiDescriptor("new-other-screen.xml")
public class NewOtherScreen extends Screen {

    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        close(new MyCloseAction("Done")); // <1>
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        closeWithDefaultAction(); // <2>
    }
}
// end::new-other-screen[]