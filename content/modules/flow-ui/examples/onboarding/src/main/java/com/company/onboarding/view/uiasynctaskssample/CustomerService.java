package com.company.onboarding.view.uiasynctaskssample;

import com.company.onboarding.entity.Customer;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    @Autowired
    protected DataManager dataManager;

    public List<Customer> loadCustomers() {
        Customer customer = dataManager.create(Customer.class);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        return List.of(customer);
    }

    public void synchronizeCustomers() {

    }
}
