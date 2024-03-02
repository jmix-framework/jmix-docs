package com.company.demo.service;

import com.company.demo.entity.Card;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Card.class)
public interface CardIndexDefinition {
    // tag::exclude[]
    @AutoMappedField(
            includeProperties = {"*", "customer.*"},
            excludeProperties = {"number", "customer.firstName"})
    void orderCustomerMapping();
    // end::exclude[]
}
