package ui.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

// tag::tag-field[]
@JmixEntity
@Table(name = "UIEX1_DEPARTMENT")
@Entity(name = "uiex1_Department")
public class Department {
    @InstanceName
    @Column(name = "NAME")
    private String name;

    @JoinColumn(name = "PARENT_DEPT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department parentDept;

    @JoinColumn(name = "MANAGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee manager;

    @OneToMany(mappedBy = "department")
    private List<Employee> employee;
    // end::tag-field[]

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getParentDept() {
        return parentDept;
    }

    public void setParentDept(Department parentDept) {
        this.parentDept = parentDept;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployee() {
        return employee;
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
}
// end::tag-field[]