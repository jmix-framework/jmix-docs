package sample.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.Store;

import javax.persistence.Id;
import java.util.UUID;

// tag::entity[]
// tag::custom-store[]
@Store(name = "inmem") // <1>
@JmixEntity(name = "sample_Metric", annotatedPropertiesOnly = true) // <2>
public class Metric {
// end::custom-store[]

    @JmixProperty(mandatory = true) // <3>
    @JmixId // <4>
    @JmixGeneratedValue // <5>
    private UUID id;

    @JmixProperty // <6>
    private String name;

    @JmixProperty // <6>
    private Double value;

    private Object ephemeral; // <7>

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // other getters and setters
    // end::entity[]

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

    public Object getEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(Object ephemeral) {
        this.ephemeral = ephemeral;
    }
}