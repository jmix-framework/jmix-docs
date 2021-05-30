package ui.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.UUID;

// tag::tag-field[]
// tag::twin-column[]
@JmixEntity
@Table(name = "UIEX1_EMPLOYEE")
@Entity(name = "uiex1_Employee")
public class Employee {
    @InstanceName
    @Column(name = "NAME")
    private String name;
    // end::twin-column[]

    @JoinColumn(name = "DEPARTMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
    // end::tag-field[]

    // tag::twin-column[]

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    // end::twin-column[]

    @Column(name = "SALARY")
    private Double salary;

    @Column(name = "POSITION_")
    private String position;

    public Position getPosition() {
        return position == null ? null : Position.fromId(position);
    }

    public void setPosition(Position position) {
        this.position = position == null ? null : position.getId();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
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
    // tag::tag-field[]
    // tag::twin-column[]
}
// end::twin-column[]
// end::tag-field[]
