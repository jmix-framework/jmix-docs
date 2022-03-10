package ui.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

// tag::custom-event[]
@JmixEntity
@Table(name = "UIEX1_CUSTOM_CALENDAR_EVENT")
@Entity(name = "uiex1_CustomCalendarEvent")
public class CustomCalendarEvent {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "EVENT_CAPTION")
    private String eventCaption;

    @Column(name = "EVENT_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventStartDate;

    @Column(name = "EVENT_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventEndDate;

    // end::custom-event[]

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventCaption() {
        return eventCaption;
    }

    public void setEventCaption(String eventCaption) {
        this.eventCaption = eventCaption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    // tag::custom-event[]
}
// end::custom-event[]