package com.company.onboarding.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.UUID;

// tag::Location[]
@JmixEntity
@Table(name = "LOCATION")
@Entity
public class Location {
    // end::Location[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "BUILDING_ENTRANCE")
    private Point buildingEntrance;

    @Column(name = "PATH_TO_BUILDING")
    private LineString pathToBuilding;

    @Column(name = "BUILDING_AREA")
    private Polygon buildingArea;

    @Column(name = "CITY", nullable = false)
    @NotNull
    private String city;

    @InstanceName
    @Column(name = "ADDRESS", nullable = false)
    @NotNull
    private String address;

    @Column(name = "TYPE_")
    private Integer type;

    // tag::Location[]
    @Column(name = "BUILDING", nullable = false)
    @NotNull
    private Point building;
    // end::Location[]

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "location")
    private User user;

    public Polygon getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(Polygon buildingArea) {
        this.buildingArea = buildingArea;
    }

    public LineString getPathToBuilding() {
        return pathToBuilding;
    }

    public void setPathToBuilding(LineString pathToBuilding) {
        this.pathToBuilding = pathToBuilding;
    }

    public Point getBuildingEntrance() {
        return buildingEntrance;
    }

    public void setBuildingEntrance(Point buildingEntrance) {
        this.buildingEntrance = buildingEntrance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
    // tag::Location[]
}
// end::Location[]