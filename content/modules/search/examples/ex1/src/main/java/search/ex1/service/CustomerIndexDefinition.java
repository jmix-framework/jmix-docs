package search.ex1.service;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import io.jmix.search.index.annotation.ManualMappingDefinition;
import io.jmix.search.index.mapping.MappingDefinition;
import io.jmix.search.index.mapping.MappingDefinitionElement;
import io.jmix.search.index.mapping.propertyvalue.impl.FilePropertyValueExtractor;
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
    @ManualMappingDefinition // <1>
    default MappingDefinition mapping(FilePropertyValueExtractor filePropertyValueExtractor) { // <2>
        return MappingDefinition.builder()
                .addElement(
                        MappingDefinitionElement.builder()
                                .includeProperties("*") // <3>
                                .excludeProperties("hobby", "maritalStatus") // <4>
                                .withFieldMappingStrategyClass(AutoMappingStrategy.class) // <5>
                                .withPropertyValueExtractor(filePropertyValueExtractor) // <6>
                                .build()
                )
                .build();
    }
    // end::manual-mapping[]
    // tag::interface2[]
}
// end::interface2[]