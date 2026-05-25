package com.company.mapssample.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

// tag::Location[]
@JmixEntity
@Table(name = "LOCATION")
@Entity
public class Location {

    @Column(name = "BUILDING")
    private Point building; // <1>

    // end::Location[]
    // tag::weight[]
    @Column(name = "WEIGHT")
    private Integer weight; // <2>

    // end::weight[]

    // tag::intensity[]
    @Column(name = "INTENSITY")
    private Double intensity; // <2>

    // end::intensity[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "CITY")
    private String city;

    @Column(name = "ADDRESS")
    private String address;

    public Double getIntensity() {
        return intensity;
    }

    public void setIntensity(Double intensity) {
        this.intensity = intensity;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Point getBuilding() {
        return building;
    }

    public void setBuilding(Point building) {
        this.building = building;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    // tag::Location[]
    // getters and setters
}
// end::Location[]