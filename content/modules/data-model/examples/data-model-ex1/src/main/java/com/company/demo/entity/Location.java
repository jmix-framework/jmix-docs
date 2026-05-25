package com.company.demo.entity;

import com.company.demo.entity.validation.ValidZipCode;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Comment;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

// tag::comment[]
@Comment("""
        Stores information about locations.
        Has reference to address""")
@JmixEntity
@Table(name = "LOCATION")
@Entity
public class Location {

    @Comment("Location zip code")
    @InstanceName
    @Column(name = "ZIP_CODE", nullable = false, length = 10)
    @ValidZipCode
    private String zipCode;
    // end::comment[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "ADDRESS")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}