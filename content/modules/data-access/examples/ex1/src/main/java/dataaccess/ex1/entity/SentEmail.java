package dataaccess.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLE_SENT_EMAIL")
@Entity(name = "sample_SentEmail")
public class SentEmail {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "EMAILED_TO")
    private String emailedTo;

    public String getEmailedTo() {
        return emailedTo;
    }

    public void setEmailedTo(String emailedTo) {
        this.emailedTo = emailedTo;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}