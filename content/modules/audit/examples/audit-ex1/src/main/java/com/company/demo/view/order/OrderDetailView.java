package com.company.demo.view.order;

import com.company.demo.entity.Order;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.audit.entity.EntityLogItem;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;

@Route(value = "orders/:id", layout = MainView.class)
@ViewController("Order_.detail")
@ViewDescriptor("order-detail-view.xml")
@EditedEntityContainer("orderDc")
public class OrderDetailView extends StandardDetailView<Order> {
    // tag::order-java[]
    @ViewComponent
    private CollectionLoader<EntityLogItem> entityLogItemsDl;

    @Subscribe(id = "orderDc", target = Target.DATA_CONTAINER)
    public void onOrderDcItemChange(final InstanceContainer.ItemChangeEvent<Order> event) { // <1>
        entityLogItemsDl.setParameter("entityOrder",event.getItem().getId());
        entityLogItemsDl.load();
    }
    // end::order-java[]
}