package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

@JmixEntity
public class NamedIntegerEntity {
    @InstanceName
    @JmixId
    private String name;

    private Integer value;

    public static NamedIntegerEntity create(String name, Integer value) {
        NamedIntegerEntity entity = new NamedIntegerEntity();
        entity.setName(name);
        entity.setValue(value);
        return entity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}