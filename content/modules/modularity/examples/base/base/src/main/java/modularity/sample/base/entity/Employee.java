package modularity.sample.base.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

// tag::entity[]
@JmixEntity
@Table(name = "BASE_EMPLOYEE", indexes = {
        @Index(name = "IDX_BASE_EMPLOYEE_DEPARTMENT", columnList = "DEPARTMENT_ID")
})
@Entity(name = "base_Employee")
public class Employee {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @InstanceName
    @Column(name = "LAST_NAME")
    private String lastName;

    @JoinColumn(name = "DEPARTMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    // getters and setters
    // end::entity[]

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}