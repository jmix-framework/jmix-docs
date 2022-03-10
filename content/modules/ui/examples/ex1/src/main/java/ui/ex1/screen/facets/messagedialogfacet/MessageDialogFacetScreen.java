package ui.ex1.screen.facets.messagedialogfacet;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.MessageDialogFacet;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_MessageDialogFacetScreen")
@UiDescriptor("message-dialog-facet-screen.xml")
public class MessageDialogFacetScreen extends Screen {
    // tag::show[]
    @Autowired
    private MessageDialogFacet msgDialog;

    @Subscribe("messageDialogBtn")
    public void onMessageDialogBtnClick(Button.ClickEvent event) {
        msgDialog.show();
    }
    // end::show[]

}