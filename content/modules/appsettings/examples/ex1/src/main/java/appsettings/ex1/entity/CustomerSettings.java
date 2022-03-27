package appsettings.ex1.entity;

import io.jmix.appsettings.defaults.AppSettingsDefault;
import io.jmix.appsettings.entity.AppSettingsEntity;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

// tag::settings-entity[]
@JmixEntity
@Table(name = "CUSTOMER_SETTINGS")
@Entity
public class CustomerSettings extends AppSettingsEntity {

    @Column(name = "NOTIFICATION_TEXT")
    private String notificationText;

    @AppSettingsDefault("B")
    @Column(name = "DEFAULT_GRADE")
    private String defaultGrade;

    @AppSettingsDefault("1000")
    @Column(name = "SALES_THRESHOLD", precision = 19, scale = 2)
    private BigDecimal salesThreshold;

    // getters and setters
    // end::settings-entity[]
    public BigDecimal getSalesThreshold() {
        return salesThreshold;
    }

    public void setSalesThreshold(BigDecimal salesThreshold) {
        this.salesThreshold = salesThreshold;
    }

    public CustomerGrade getDefaultGrade() {
        return defaultGrade == null ? null : CustomerGrade.fromId(defaultGrade);
    }

    public void setDefaultGrade(CustomerGrade defaultGrade) {
        this.defaultGrade = defaultGrade == null ? null : defaultGrade.getId();
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}