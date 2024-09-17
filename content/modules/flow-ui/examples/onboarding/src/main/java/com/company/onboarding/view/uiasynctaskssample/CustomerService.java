package com.company.onboarding.view.uiasynctaskssample;

import com.company.onboarding.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

    public List<Customer> loadCustomers() {
        return List.of();
    }

    public void synchronizeCustomers() {

    }
}
