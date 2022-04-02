package charts.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;

import java.util.UUID;

// tag::country-growth[]
@JmixEntity
public class CountryGrowth {
    @JmixProperty(mandatory = true)
    @JmixId
    @JmixGeneratedValue
    protected UUID id;

    @JmixProperty(mandatory = true)
    @InstanceName
    protected String country;

    @JmixProperty(mandatory = true)
    protected Double year2020;

    @JmixProperty(mandatory = true)
    protected Double year2021;

    public CountryGrowth() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setYear2020(Double year2020) {
        this.year2020 = year2020;
    }

    public Double getYear2020() {
        return year2020;
    }

    public void setYear2021(Double year2021) {
        this.year2021 = year2021;
    }

    public Double getYear2021() {
        return year2021;
    }
}
// end::country-growth[]