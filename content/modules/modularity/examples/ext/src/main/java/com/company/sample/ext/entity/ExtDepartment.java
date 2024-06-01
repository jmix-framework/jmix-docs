package com.company.sample.ext.entity;

import com.company.sample.base.entity.Department;
import io.jmix.core.entity.annotation.ReplaceEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import jakarta.persistence.*;

// tag::entity[]
@JmixEntity
@Entity
@ReplaceEntity(Department.class) // <1>
public class ExtDepartment extends Department { // <2>

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description; // <3>

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID")
    private User manager; // <3>

    // getters and setters
    // end::entity[]

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