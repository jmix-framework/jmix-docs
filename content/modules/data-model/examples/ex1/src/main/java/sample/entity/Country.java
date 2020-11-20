package sample.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// tag::store-ann[]
@Store(name = "locations")
@JmixEntity
@Table(name = "SAMPLE_COUNTRY")
@Entity(name = "sample_Country")
public class Country {
// end::store-ann[]
    @Id
    @Column(name = "ID", nullable = false)
    private String code;

    @InstanceName
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}