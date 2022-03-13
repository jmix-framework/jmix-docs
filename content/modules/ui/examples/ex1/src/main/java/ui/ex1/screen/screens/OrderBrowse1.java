package ui.ex1.screen.screens;

import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Order;

@UiController("uiex1_Order1.browse")
@UiDescriptor("order-browse1.xml")
@LookupComponent("ordersTable")
public class OrderBrowse1 extends StandardLookup<Order> {

    // tag::before-show1[]
    @Autowired
    private CollectionLoader<Order> ordersDl;

    // end::before-show1[]

    // tag::after-show1[]
    @Autowired
    private Notifications notifications;

    // end::after-show1[]

    // tag::init-event[]
    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(InitEvent event) {
        Label<String> label = uiComponents.create(Label.TYPE_STRING);
        label.setValue("Orders list");
        getWindow().add(label);
    }
    // end::init-event[]

    // tag::before-show2[]
    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        ordersDl.load();
    }
    // end::before-show2[]

    // tag::after-show2[]
    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        notifications.create().withCaption("Just opened").show();
    }
    // end::after-show2[]
}