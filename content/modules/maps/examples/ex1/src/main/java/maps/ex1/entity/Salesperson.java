package maps.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "MAPST_SALESPERSON")
@Entity(name = "mapst_Salesperson")
public class Salesperson {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Geometry
    @PropertyDatatype("geoPoint")
    @Column(name = "LOCATION")
    @JmixProperty
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERRITORY_ID")
    private Territory territory;

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}