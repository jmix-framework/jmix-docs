package com.company.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@JmixEntity
@Table(name = "TEMPERATURE_DATA")
@Entity
public class TemperatureData {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "TEMPERATURE", nullable = false)
    private Integer temperature;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "MONTH_", nullable = false)
    private String month;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Month getMonth() {
        return Month.fromId(month);
    }

    public void setMonth(Month month) {
        this.month = month.getId();
    }
}