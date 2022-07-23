package modularity.sample.ext.entity;

import modularity.sample.base.entity.Department;
import io.jmix.core.entity.annotation.ReplaceEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;

// tag::entity[]
@JmixEntity
@Entity
@ReplaceEntity(Department.class) // <1>
public class ExtDepartment extends Department { // <2>

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description; // <3>

    // getters and setters
    // end::entity[]

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private User manager;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}