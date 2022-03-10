package ui.ex1.screen.screens;

import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;

// tag::common-annotations[]
@UiController("sample_OrderScreen")
@UiDescriptor("order-screen.xml")
@MultipleOpen
@DialogMode(forceDialog = true)
public class OrderScreen extends Screen {
    // end::common-annotations[]

    // tag::close[]
    @Subscribe("closeBtn")
    public void onCloseBtnClick(Button.ClickEvent event) {
        close(StandardOutcome.CLOSE);
    }
    // end::close[]

    // tag::get-screen-data[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        getScreenData().loadAll();
    }
    // end::get-screen-data[]

}
