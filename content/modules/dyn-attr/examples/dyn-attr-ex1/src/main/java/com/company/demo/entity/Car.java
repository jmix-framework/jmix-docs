package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.dynattr.model.Categorized;
import io.jmix.dynattr.model.Category;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "CAR", indexes = {
        @Index(name = "IDX_CAR_CATEGORY", columnList = "CATEGORY_ID")
})
@Entity
public class Car implements Categorized {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NUMBER_")
    private String number;

    @Column(name = "MODEL")
    private String model;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}