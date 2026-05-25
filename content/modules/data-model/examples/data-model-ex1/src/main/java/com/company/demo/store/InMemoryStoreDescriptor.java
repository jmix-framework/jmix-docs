package com.company.demo.store;

import io.jmix.core.metamodel.model.StoreDescriptor;
import org.springframework.stereotype.Component;

// tag::custom-store[]
@Component
public class InMemoryStoreDescriptor implements StoreDescriptor {

    @Override
    public String getBeanName() {
        return "inMemoryStore";
    }

    @Override
    public boolean isJpa() {
        return false;
    }
}
// end::custom-store[]
