package sample.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

// tag::entity[]
@JmixEntity // <1>
@Table(name = "SAMPLE_CUSTOMER") // <2>
@Entity(name = "sample_Customer") // <3>
public class Customer {

    @JmixGeneratedValue // <4>
    @Id // <5>
    @Column(name = "ID", nullable = false) // <6>
    private UUID id;

    @Version // <7>
    @Column(name = "VERSION")
    private Integer version;

    @InstanceName // <8>
    @NotNull // <9>
    @Column(name = "NAME", nullable = false)
    private String name;

    @Email // <9>
    @Column(name = "EMAIL", unique = true) // <10>
    private String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // other getters and setters

    // end::entity[]

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}