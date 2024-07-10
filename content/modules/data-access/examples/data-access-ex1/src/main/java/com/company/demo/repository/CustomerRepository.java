package com.company.demo.repository;

import com.company.demo.entity.Customer;
import io.jmix.core.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

// tag::data-repository[]
public interface CustomerRepository extends JmixDataRepository<Customer, UUID> {
    // end::data-repository[]

    // tag::name-based-method[]
    List<Customer> findByEmailContainingIgnoreCase(String emailPart);
    // end::name-based-method[]

    // tag::paging[]
    Page<Customer> findByEmailContainingIgnoreCase(String emailPart, Pageable pageable);
    // end::paging[]

    // tag::fetch-plan[]
    List<Customer> findByEmailContainingIgnoreCase(String emailPart, FetchPlan fetchPlan);
    // end::fetch-plan[]

    // tag::query[]
    @Query("select c from sample_Customer c where c.email like :email")
    List<Customer> findCustomersByEmail(@Param("email") String emailPart);
    // end::query[]

// tag::data-repository-end[]
}
// end::data-repository-end[]
