package ui.ex1.screen.urlhistorynav;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.screen.entity.customer.CustomerBrowse;

@UiController("sample_UrlRoutesGeneratorScreen")
@UiDescriptor("url-routes-generator-screen.xml")
public class UrlRoutesGeneratorScreen extends Screen {
    @Autowired
    protected UrlRouting urlRouting;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected EntityComboBox<Customer> customerField;

    @Subscribe("btn1")
    protected void onBtn1Click(Button.ClickEvent event) {
        // tag::get-route-by-screen-id[]
        String route = urlRouting.getRouteGenerator().getRoute("uiex1_Customer.browse");
        // end::get-route-by-screen-id[]
        notifications.create()
                .withCaption(route)
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    @Subscribe("btn2")
    protected void onBtn2Click(Button.ClickEvent event) {
        // tag::get-route-by-screen-class[]
        String route = urlRouting.getRouteGenerator().getRoute(CustomerBrowse.class);
        // end::get-route-by-screen-class[]
        notifications.create()
                .withCaption(route)
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    @Subscribe("btn3")
    protected void onBtn3Click(Button.ClickEvent event) {
        // tag::get-editor-route[]
        Customer e = customerField.getValue();

        String route = urlRouting.getRouteGenerator().getEditorRoute(e);
        // end::get-editor-route[]
        notifications.create()
                .withCaption(route)
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }
}