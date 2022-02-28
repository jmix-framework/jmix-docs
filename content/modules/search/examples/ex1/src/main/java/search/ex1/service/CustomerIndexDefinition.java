package search.ex1.service;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import io.jmix.search.index.mapping.MappingDefinition;
import io.jmix.search.index.mapping.MappingDefinitionElement;
import io.jmix.search.index.mapping.strategy.impl.AutoMappingStrategy;
import search.ex1.entity.Customer;

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
                .addElement(
                        MappingDefinitionElement.builder()
                                .includeProperties("*")
                                .withFieldMappingStrategyClass(AutoMappingStrategy.class)
                                .build()
                )
                .build();
    }
    // end::manual-mapping[]
    // tag::interface2[]
}
// end::interface2[]