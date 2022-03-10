package ui.ex1.screen.containers.tabsheet;

import io.jmix.ui.component.Label;
import io.jmix.ui.component.TabSheet;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_TabSheetScreen")
@UiDescriptor("tab-sheet-screen.xml")
public class TabSheetScreen extends Screen {
    // tag::lazy-tab[]
    @Autowired
    private Label<String> info;
    @Autowired
    private TabSheet tabSheet;

    @Subscribe
    public void onInit(InitEvent event) {
        checkComponents();
    }

    @Subscribe("tabSheet")
    protected void onTabSheetSelectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        if (event.getSelectedTab().getName().equals("tab2")) {
            checkComponents();
        }
    }

    private void checkComponents() {
        StringBuilder sb = new StringBuilder("Created components:\n");

        sb.append("label1 = ");
        Label<String> label1 = (Label<String>) tabSheet.getComponentNN("label1");
        sb.append(label1.getValue());

        try{
            Label<String> label2 = (Label<String>) tabSheet.getComponentNN("label2");
            sb.append(", label2 = ");
            sb.append(label2.getValue());
        } catch (IllegalArgumentException e){
            sb.append(", label2 was not found");
        }
        info.setValue(sb.toString());
    }
    // end::lazy-tab[]
}