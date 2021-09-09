package ui.ex1.entity;

import io.jmix.core.entity.annotation.CurrencyValue;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

// tag::product[]
@JmixEntity
@Table(name = "UIEX1_PRODUCT")
@Entity(name = "UIEX1_Product")
public class Product {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "PRICE", nullable = false)
    @CurrencyValue(currency = "USD")
    protected BigDecimal price;
    // ...
    // end::product[]

    @Column(name = "NAME", nullable = false)
    @InstanceName
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    // tag::product[]
}
// end::product[]