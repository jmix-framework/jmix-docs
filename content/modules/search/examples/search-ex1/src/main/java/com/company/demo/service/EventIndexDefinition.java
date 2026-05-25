package com.company.demo.service;

import com.company.demo.entity.Event;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;

@JmixEntitySearchIndex(entity = Event.class)
public interface EventIndexDefinition {

    // tag::file[]
    @AutoMappedField(
            includeProperties = {"*"},
            indexFileContent = false)
    void fileMapping();
    // end::file[]

}
