package com.company.sample.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "BOOKING", indexes = {
        @Index(name = "IDX_BOOKING_CREATOR", columnList = "CREATOR_ID")
})
@Entity
public class Booking {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @InstanceName
    @Column(name = "TITLE", nullable = false)
    @NotNull
    private String title;
    @JoinColumn(name = "CREATOR_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User creator;
    @NotNull
    @Column(name = "ROOM", nullable = false)
    private String room;
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;
    @Column(name = "START_DATE", nullable = false)
    @NotNull
    private LocalDateTime startDate;
    @Column(name = "END_DATE", nullable = false)
    @NotNull
    private LocalDateTime endDate;

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Room getRoom() {
        return room == null ? null : Room.fromId(room);
    }

    public void setRoom(Room room) {
        this.room = room == null ? null : room.getId();
    }
}
