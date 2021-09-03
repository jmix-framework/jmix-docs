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
    protected Double year2014;

    @JmixProperty(mandatory = true)
    protected Double year2015;

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

    public void setYear2014(Double year2014) {
        this.year2014 = year2014;
    }

    public Double getYear2014() {
        return year2014;
    }

    public void setYear2015(Double year2015) {
        this.year2015 = year2015;
    }

    public Double getYear2015() {
        return year2015;
    }
}
// end::country-growth[]