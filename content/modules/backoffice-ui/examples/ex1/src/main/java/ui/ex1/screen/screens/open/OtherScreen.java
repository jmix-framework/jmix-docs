package ui.ex1.screen.screens.open;

import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;

// tag::other-screen[]
@UiController("sample_OtherScreen")
@UiDescriptor("other-screen.xml")
public class OtherScreen extends Screen {

    private String result;

    public String getResult() {
        return result;
    }

    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        result = "Done";
        close(StandardOutcome.COMMIT); // <1>
    }

    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE); // <2>
    }
}
// end::other-screen[]