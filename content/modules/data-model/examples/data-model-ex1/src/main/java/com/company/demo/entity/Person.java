package com.company.demo.entity;

import com.company.demo.entity.validation.ValidPassportNumber;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import java.math.BigDecimal;
import java.util.UUID;

// tag::custom-validation-annotation[]
@ValidPassportNumber(groups = {Default.class, UiCrossFieldChecks.class})
// end::custom-validation-annotation[]
// tag::entity-start[]
@JmixEntity
@Table(name = "PERSON", indexes = {
        @Index(name = "IDX_PERSON_LOCATION_ID", columnList = "LOCATION_ID")
})
@Entity
public class Person {
    // end::entity-start[]
    // tag::entity-fields[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Length(min = 3) // <1>
    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull
    private String firstName;

    @Email(message = "Email address has invalid format: ${validatedValue}",
            regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$") // <2>
    @Column(name = "EMAIL", length = 120)
    private String email;

    @DecimalMin(message = "Person height should be positive",
            value = "0", inclusive = false) // <3>
    @DecimalMax(message = "Person height can not exceed 300 centimeters",
            value = "300") // <4>
    @Column(name = "HEIGHT", precision = 19, scale = 2)
    private BigDecimal height;

    @Column(name = "PASSPORT_NUMBER", nullable = false, length = 15)
    @NotNull
    private String passportNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;
    // end::entity-fields[]

    // tag::message1[]
    @Pattern(message = "Bad formed person last name: ${validatedValue}",
            regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$")
    @Column(name = "LAST_NAME", nullable = false)
    @NotNull
    private String lastName;

    // end::message1[]
    // tag::message2[]
    @Min(message = "{msg://com.company.demo.entity/Person.age.validation.Min}", value = 14)
    @Column(name = "AGE")
    private Integer age;

    // end::message2[]
    // tag::message3[]
    @Pattern(message = "Invalid name: ${validatedValue}, pattern: {regexp}",
            regexp = "^[A-Z][a-z]*(\\s(([a-z]{1,3})|(([a-z]+\\')?[A-Z][a-z]*)))*$")
    @Column(name = "FULL_NAME")
    private String fullName;
    // end::message3[]

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    // tag::entity-end[]
}
// end::entity-end[]