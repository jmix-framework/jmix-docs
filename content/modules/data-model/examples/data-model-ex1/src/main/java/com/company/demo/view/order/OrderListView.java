package com.company.demo.view.order;

import com.company.demo.entity.Order;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.datatype.DatatypeRegistry;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route(value = "orders", layout = MainView.class)
@ViewController("Order_.list")
@ViewDescriptor("order-list-view.xml")
@LookupComponent("ordersDataGrid")
@DialogMode(width = "64em")
public class OrderListView extends StandardListView<Order> {

    // tag::datatype[]
    @ViewComponent
    private TypedTextField<BigDecimal> amountField;

    @Autowired
    private DatatypeRegistry datatypeRegistry;

    @Subscribe
    public void onInit(InitEvent event) {
        Datatype<BigDecimal> datatype = datatypeRegistry.get(BigDecimal.class);
        amountField.setDatatype(datatype);
    }
    // end::datatype[]
}