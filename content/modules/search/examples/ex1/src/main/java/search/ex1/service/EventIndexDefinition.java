package search.ex1.service;

import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import search.ex1.entity.Event;

@JmixEntitySearchIndex(entity = Event.class)
public interface EventIndexDefinition {

    // tag::file[]
    @AutoMappedField(
            includeProperties = {"*"},
            indexFileContent = false)
    void fileMapping();
    // end::file[]

}
