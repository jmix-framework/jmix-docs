package sample.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.Store;

import javax.persistence.Id;
import java.util.UUID;

// tag::custom-store[]
@Store(name = "inmem")
@JmixEntity(name = "sample_Metric")
public class Metric {
// end::custom-store[]

    @JmixProperty(mandatory = true)
    @JmixGeneratedValue
    @Id
    private UUID id;

    @InstanceName
    @JmixProperty
    private String name;

    @JmixProperty
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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