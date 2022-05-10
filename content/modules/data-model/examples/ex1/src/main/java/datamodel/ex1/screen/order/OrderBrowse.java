package datamodel.ex1.screen.order;

import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.datatype.DatatypeRegistry;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("datamodel.ex1.sample_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
    // tag::datatype[]
    @Autowired
    private TextField<BigDecimal> amountField;

    @Autowired
    private DatatypeRegistry datatypeRegistry;

    @Subscribe
    public void onInit(InitEvent event) {
        Datatype<BigDecimal> datatype = datatypeRegistry.get(BigDecimal.class);
        amountField.setDatatype(datatype);
    }
    // end::datatype[]
}