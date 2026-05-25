package search.ex1.service;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import search.ex1.entity.Order;

// tag::index-definition1[]
@JmixEntitySearchIndex(entity = Order.class)
public interface OrderIndexDefinition {

    // end::index-definition1[]
    // tag::order-mapping[]
    @AutoMappedField(includeProperties =
            {"number", "product", "customer.status", "customer.lastName"})
    void orderMapping();
    // end::order-mapping[]

    // tag::index-definition2[]
}
// end::index-definition2[]
