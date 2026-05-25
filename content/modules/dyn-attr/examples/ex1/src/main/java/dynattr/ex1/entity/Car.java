package dynattr.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.dynattr.model.Categorized;
import io.jmix.dynattr.model.Category;

import javax.persistence.*;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLE_CAR")
@Entity(name = "sample_Car")
public class Car implements Categorized {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "NUMBER_")
    private String number;

    @Column(name = "MODEL")
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

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

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }
}