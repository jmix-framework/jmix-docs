package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "FORMATTERS")
@Entity
public class Formatters {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    // tag::number-format[]
    @Column(name = "PRECISE_NUMBER", precision = 19, scale = 4)
    @NumberFormat(pattern = "0.0000")
    protected BigDecimal preciseNumber;

    @Column(name = "WEIRD_NUMBER", precision = 19, scale = 4)
    @NumberFormat(pattern = "#,##0.0000", decimalSeparator = "_", groupingSeparator = "`")
    protected BigDecimal weirdNumber;

    @Column(name = "SIMPLE_NUMBER")
    @NumberFormat(pattern = "#")
    protected Integer simpleNumber;

    @Column(name = "PERCENT_NUMBER", precision = 19, scale = 4)
    @NumberFormat(pattern = "#%")
    protected BigDecimal percentNumber;
    // end::number-format[]
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}