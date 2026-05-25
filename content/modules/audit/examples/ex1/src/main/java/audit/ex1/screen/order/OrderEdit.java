package audit.ex1.screen.order;

import io.jmix.audit.EntityLog;
import io.jmix.audit.entity.EntityLogItem;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import audit.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ex1_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {

    // tag::order-java[]
    @Autowired
    private InstanceLoader<Order> orderDl;
    @Autowired
    private CollectionLoader<EntityLogItem> entityLogItemsDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) { //<1>
        orderDl.load();
    }

    @Subscribe(id = "orderDc", target = Target.DATA_CONTAINER)
    public void onOrderDcItemChange(InstanceContainer.ItemChangeEvent<Order> event) { //<2>
        entityLogItemsDl.setParameter("order", event.getItem().getId());
        entityLogItemsDl.load();
    }
    // end::order-java[]
}