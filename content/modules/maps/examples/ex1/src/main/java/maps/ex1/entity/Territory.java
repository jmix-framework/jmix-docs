package maps.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "MAPST_TERRITORY")
@Entity(name = "mapst_Territory")
// tag::entity-begin[]
@JmixEntity
public class Territory {
    //...
    // end::entity-begin[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    // tag::geo-object[]
    @Geometry
    @PropertyDatatype("geoPolygon")
    @Column(name = "POLYGON")
    @JmixProperty
    private Polygon polygon;
    // end::geo-object[]

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
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
// tag::entity-end[]
    //...
}
// end::entity-end[]