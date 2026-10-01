package com.company.sample.app;

import com.company.sample.entity.Customer;
import io.jmix.core.DataManager;
import io.jmix.dynmodel.DynamicModelOperation;
import org.springframework.stereotype.Component;

// tag::operation[]
@Component
public class CustomerOperations {

    private final DataManager dataManager;

    public CustomerOperations(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @DynamicModelOperation("checkCustomerName")
    public String checkCustomerName(Customer customer) {
        long count = dataManager.loadValue(
                        "select count(e) from Customer e where e.name = :name and e.id <> :id", Long.class)
                .parameter("name", customer.getName())
                .parameter("id", customer.getId())
                .one();
        return count == 0 ? "The name is not used" : "Another customer has this name";
    }
}
// end::operation[]
