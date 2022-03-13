package ui.ex1.screen.formatter;

import io.jmix.ui.component.Table;
import io.jmix.ui.component.formatter.CollectionFormatter;
import io.jmix.ui.component.formatter.DateFormatter;
import io.jmix.ui.component.formatter.NumberFormatter;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Order;

@UiController("sample_FormatterScreen")
@UiDescriptor("formatter-screen.xml")
public class FormatterScreen extends Screen {
    // tag::inject-application-context[]
    @Autowired
    protected ApplicationContext applicationContext;

    // end::inject-application-context[]
    // tag::inject-orders-table[]
    @Autowired
    protected Table<Order> ordersTable1;

    // end::inject-orders-table[]

    // tag::inject-customers-table[]
    @Autowired
    protected Table<Customer> customersTable;

    // end::inject-customers-table[]

    // tag::init-start[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::init-start[]
        // tag::set-formatter[]
        NumberFormatter formatter = applicationContext.getBean(NumberFormatter.class); // <1>
        formatter.setFormat("#,##0.00");
        ordersTable1.getColumn("amount").setFormatter(formatter);
        // end::set-formatter[]
        // tag::set-date-formatter[]
        DateFormatter dateFormatter = applicationContext.getBean(DateFormatter.class);
        dateFormatter.setFormat("h:mm a");
        ordersTable1.getColumn("deliveryTime").setFormatter(dateFormatter);
        // end::set-date-formatter[]
        // tag::set-custom-formatter[]
        customersTable.getColumn("age").setFormatter(value -> value + " y.o.");
        // end::set-custom-formatter[]
        // tag::set-collection-formatter[]
        CollectionFormatter collectionFormatter = applicationContext.getBean(CollectionFormatter.class);
        customersTable.getColumn("favouriteBrands").setFormatter(collectionFormatter);
        // end::set-collection-formatter[]
        // tag::init-end[]
    }
    // end::init-end[]
}