package com.company.clientapp.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.Store;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

// tag::restds-entity[]
@Store(name = "serviceapp")
@JmixEntity
public class Region {
    @JmixGeneratedValue
    @JmixId
    private UUID id;

    private Integer version;

    @InstanceName
    @NotNull
    private String name;

    // getters and setters
    // end::restds-entity[]

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}