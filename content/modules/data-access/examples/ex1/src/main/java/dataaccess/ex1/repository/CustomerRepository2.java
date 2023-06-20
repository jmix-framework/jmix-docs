package dataaccess.ex1.repository;

import dataaccess.ex1.entity.Customer;
import io.jmix.core.repository.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository2 extends JmixDataRepository<Customer, UUID> {

    // tag::fetch-plan[]
    @FetchPlan("customer-minimal")
    List<Customer> findByEmail(String email);
    // end::fetch-plan[]
}
