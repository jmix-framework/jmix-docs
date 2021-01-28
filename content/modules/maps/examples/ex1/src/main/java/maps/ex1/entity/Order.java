package maps.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.PropertyDatatype;
import io.jmix.maps.Geometry;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Table(name = "MAPST_ORDER")
@Entity(name = "mapst_Order")
// tag::entity-begin[]
@JmixEntity
public class Order {
    //...
    // end::entity-begin[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    private Date date;

    @Column(name = "PRODUCT")
    private String product;

    @Column(name = "AMOUNT")
    private Integer amount;

    // tag::geo-object[]
    @Geometry
    @PropertyDatatype("geoPoint")
    @Column(name = "LOCATION")
    @JmixProperty
    private Point location;
    // end::geo-object[]

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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