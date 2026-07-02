package com.company.onboarding.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.flowui.view.template.DetailViewTemplate;
import io.jmix.flowui.view.template.ListViewTemplate;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

// tag::class[]
@ListViewTemplate(parentMenu = "application")
@DetailViewTemplate
@JmixEntity
@Entity
@Table(name = "PRODUCT")
public class Product {
    // end::class[]

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE", precision = 19, scale = 2)
    private BigDecimal price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
