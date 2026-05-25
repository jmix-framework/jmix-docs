package com.company.demo.repository;

import com.company.demo.entity.Customer;
import io.jmix.core.repository.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.QueryHints;
import io.jmix.data.PersistenceHints;
import jakarta.persistence.QueryHint;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository3 extends JmixDataRepository<Customer, UUID> {

    // tag::cacheable[]
    @QueryHints(@QueryHint(name = PersistenceHints.CACHEABLE, value = "true"))
    List<Customer> findByEmail(String email);
    // end::cacheable[]
}
