package com.company.onboarding.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.maps.Geometry;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Point;

import java.util.UUID;

@JmixEntity
@Table(name = "LOCATION")
@Entity
public class Location {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "CITY", nullable = false)
    @NotNull
    private String city;

    @InstanceName
    @Column(name = "ADDRESS", nullable = false)
    @NotNull
    private String address;

    @Column(name = "TYPE_")
    private Integer type;

    @Geometry
    @Column(name = "BUILDING", nullable = false)
    @NotNull
    private Point building;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public Point getBuilding() {
        return building;
    }

    public void setBuilding(Point building) {
        this.building = building;
    }

    public LocationType getType() {
        return type == null ? null : LocationType.fromId(type);
    }

    public void setType(LocationType type) {
        this.type = type == null ? null : type.getId();
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