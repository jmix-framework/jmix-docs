package com.company.onboarding.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.LookupField;
import io.jmix.core.entity.annotation.LookupItemsQuery;
import io.jmix.core.entity.annotation.LookupType;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "DEPARTMENT", indexes = {
        @Index(name = "IDX_DEPARTMENT_HR_MANAGER", columnList = "HR_MANAGER_ID"),
        @Index(name = "IDX_DEPARTMENT_PARENT_DEPARTMENT", columnList = "PARENT_DEPARTMENT_ID")
})
@Entity
public class Department {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    // tag::lookup-field-query[]
    @LookupField(type = LookupType.DROPDOWN,
            actions = {"entity_lookup", "entity_clear"},
            itemsQuery = @LookupItemsQuery(
                    query = "select e from Department e where e.name like :searchString escape '\\' order by e.name",
                    searchStringFormat = "(?i)%${inputString}%",
                    escapeValueForLike = true))
    @JoinColumn(name = "PARENT_DEPARTMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department parentDepartment;
    // end::lookup-field-query[]

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    // tag::lookup-field-instance-name[]
    @LookupField(type = LookupType.DROPDOWN,
            itemsQuery = @LookupItemsQuery(byInstanceName = true))
    @JoinColumn(name = "HR_MANAGER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User hrManager;
    // end::lookup-field-instance-name[]

    @Column(name = "NUM")
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    public User getHrManager() {
        return hrManager;
    }

    public void setHrManager(User hrManager) {
        this.hrManager = hrManager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}