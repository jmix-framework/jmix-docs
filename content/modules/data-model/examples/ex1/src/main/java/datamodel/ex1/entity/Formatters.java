package datamodel.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLE_FORMATTERS")
@Entity(name = "sample_Formatters")
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