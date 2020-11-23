package sample.entity;


import io.jmix.core.Messages;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// tag::instance-name[]
@JmixEntity(name = "sample_GeoPoint")
@Embeddable
public class GeoPoint {

    @Column(name = "LAT")
    protected Double latitude;

    @Column(name = "LON")
    protected Double longitude;

    @InstanceName
    @DependsOnProperties({"latitude", "longitude"})
    public String getDisplayName(Messages messages) {
        return messages.formatMessage(
                getClass(), "GeoPoint.instanceName", this.latitude, this.longitude);
    }
    // end::instance-name[]

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
