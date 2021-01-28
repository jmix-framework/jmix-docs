package datamodel.ex1.bean;

import datamodel.ex1.entity.Order;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    // tag::metadata-create[]
    @Autowired
    private Metadata metadata;

    Order createOrder() {
        return metadata.create(Order.class);
    }
    // end::metadata-create[]

    // tag::data-manager-create[]
    @Autowired
    private DataManager dataManager;

    Order createAndSaveOrder(String number) {
        Order order = dataManager.create(Order.class);
        order.setNum(number);
        dataManager.save(order);
        return order;
    }
    // end::data-manager-create[]

}
