package com.company.demo.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

// tag::entity[]
@JmixEntity // <1>
public class ProductPart {

    @JmixProperty(mandatory = true) // <2>
    @InstanceName // <3>
    private String name;

    private Integer quantity; // <4>

    // getters and setters
    // end::entity[]

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}