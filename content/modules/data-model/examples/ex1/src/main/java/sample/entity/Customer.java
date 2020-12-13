package sample.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
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

    // tag::cross-datastore-ref[]
    @SystemLevel
    @Column(name = "ADDRESS_ID")
    private UUID addressId; // <1>

    @Transient
    @JmixProperty
    @DependsOnProperties("addressId")
    private Address address; // <2>

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // end::cross-datastore-ref[]

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

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