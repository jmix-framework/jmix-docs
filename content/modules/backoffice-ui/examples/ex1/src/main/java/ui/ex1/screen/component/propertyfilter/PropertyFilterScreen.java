package ui.ex1.screen.component.propertyfilter;

import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("propertyFilterScreen")
@UiDescriptor("property-filter-screen.xml")
public class PropertyFilterScreen extends Screen {
    @Autowired
    private DataManager dataManager;

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        // tag::property-condition[]
        dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("firstName", "a"))
                .list();
        // end::property-condition[]
    }
}