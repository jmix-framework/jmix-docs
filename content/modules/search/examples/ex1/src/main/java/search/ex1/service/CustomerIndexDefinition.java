package search.ex1.service;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import io.jmix.search.index.mapping.processor.MappingDefinition;
import io.jmix.search.index.mapping.strategy.AutoMappingStrategy;
import search.ex1.entity.Customer;
import search.ex1.entity.Order;

// tag::interface1[]
@JmixEntitySearchIndex(entity = Customer.class)
public interface CustomerIndexDefinition {

    // end::interface1[]

    // tag::method[]
    @AutoMappedField(
            includeProperties = {"firstName", "lastName"},
            analyzer = "russian")
    void customerMapping();
    // end::method[]

    // tag::manual-mapping[]
    default MappingDefinition mapping() {
        return MappingDefinition.builder()
                .newElement()
                .includeProperties("*")
                .usingFieldMappingStrategyClass(AutoMappingStrategy.class)
                .buildElement()
                .buildMappingDefinition();
    }
    // end::manual-mapping[]
    // tag::interface2[]
}
// end::interface2[]