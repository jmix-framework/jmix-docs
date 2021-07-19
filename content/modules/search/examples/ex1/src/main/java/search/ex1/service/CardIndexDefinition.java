package search.ex1.service;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import search.ex1.entity.Card;
import search.ex1.entity.User;

@JmixEntitySearchIndex(entity = Card.class)
public interface CardIndexDefinition {

    // tag::exclude[]
    @AutoMappedField(
            includeProperties = {"*", "customer.*"},
            excludeProperties = {"number", "customer.firstName"})
    void orderCustomerMapping();
    // end::exclude[]

}

