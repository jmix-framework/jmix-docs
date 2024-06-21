package com.company.demo.service;

import com.company.demo.entity.Order;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

// tag::index-definition[]
@JmixEntitySearchIndex(entity = Order.class)
public interface OrderIndexDefinition {

    // end::index-definition[]
    // tag::order-mapping[]
    @AutoMappedField(includeProperties =
            {"number", "product", "customer.status", "customer.lastName"})
    void orderMapping();
    // end::order-mapping[]
    // tag::index-definition[]
}
// end::index-definition[]