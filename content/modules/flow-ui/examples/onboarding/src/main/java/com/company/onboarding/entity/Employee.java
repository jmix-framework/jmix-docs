package com.company.onboarding.entity;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

// tag::employee-entity[]
@JmixEntity
@Table(name = "SAMPLER_EMPLOYEE")
@Entity(name = "sampler_Employee")
public class Employee {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    /* other attributes */

    //end::employee-entity[]

    @InstanceName
    @Column(name = "NAME")
    private String name;

    // tag::employee-entity[]
    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "ADDRESS_COUNTRY_ID")),
            @AssociationOverride(name = "city", joinColumns = @JoinColumn(name = "ADDRESS_CITY_ID"))
    })
    @AttributeOverrides({
            @AttributeOverride(name = "zip", column = @Column(name = "ADDRESS_ZIP")),
            @AttributeOverride(name = "addressLine", column = @Column(name = "ADDRESS_ADDRESS_LINE"))
    })
    private Address address;

    //getters and setters are omitted for brevity
    //end::employee-entity[]

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // tag::employee-entity[]
}
//end::employee-entity[]