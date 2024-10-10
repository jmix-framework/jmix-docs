package com.company.fullcalendarsample.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.fullcalendarflowui.component.model.DaysOfWeek;
import io.jmix.fullcalendarflowui.component.model.Display;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@JmixEntity
@Table(name = "EVENT")
@Entity
public class Event {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "GROUP_ID")
    private String groupId;

    @InstanceName
    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_DATE")
    private OffsetDateTime startDate;

    @Column(name = "END_DATE")
    private OffsetDateTime endDate;

    @Column(name = "ALL_DAY")
    private Boolean allDay;

    @Column(name = "INTERACTIVE")
    private Boolean interactive;

    @Column(name = "CLASS_NAMES")
    private String classNames;

    @Column(name = "START_EDITABLE")
    private Boolean startEditable;

    @Column(name = "DURATION_EDITABLE")
    private Boolean durationEditable;

    @Column(name = "DISPLAY")
    private String display;

    @Column(name = "OVERLAP")
    private Boolean overlap;

    @Column(name = "BACKGROUND_COLOR")
    private String backgroundColor;

    @Column(name = "BORDER_COLOR")
    private String borderColor;

    @Column(name = "TEXT_COLOR")
    private String textColor;

    @Column(name = "RECURRING_DAYS_OF_WEEK")
    private DaysOfWeek recurringDaysOfWeek;

    @Column(name = "RECURRING_START_DATE")
    private LocalDate recurringStartDate;

    @Column(name = "RECURRING_END_DATE")
    private LocalDate recurringEndDate;

    @Column(name = "RECURRING_START_TIME")
    private LocalTime recurringStartTime;

    @Column(name = "RECURRING_END_TIME")
    private LocalTime recurringEndTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public LocalTime getRecurringEndTime() {
        return recurringEndTime;
    }

    public void setRecurringEndTime(LocalTime recurringEndTime) {
        this.recurringEndTime = recurringEndTime;
    }

    public LocalTime getRecurringStartTime() {
        return recurringStartTime;
    }

    public void setRecurringStartTime(LocalTime recurringStartTime) {
        this.recurringStartTime = recurringStartTime;
    }

    public LocalDate getRecurringEndDate() {
        return recurringEndDate;
    }

    public void setRecurringEndDate(LocalDate recurringEndDate) {
        this.recurringEndDate = recurringEndDate;
    }

    public LocalDate getRecurringStartDate() {
        return recurringStartDate;
    }

    public void setRecurringStartDate(LocalDate recurringStartDate) {
        this.recurringStartDate = recurringStartDate;
    }

    public DaysOfWeek getRecurringDaysOfWeek() {
        return recurringDaysOfWeek;
    }

    public void setRecurringDaysOfWeek(DaysOfWeek recurringDaysOfWeek) {
        this.recurringDaysOfWeek = recurringDaysOfWeek;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean getOverlap() {
        return overlap;
    }

    public void setOverlap(Boolean overlap) {
        this.overlap = overlap;
    }

    public Display getDisplay() {
        return display == null ? null : Display.fromId(display);
    }

    public void setDisplay(Display display) {
        this.display = display == null ? null : display.getId();
    }

    public Boolean getDurationEditable() {
        return durationEditable;
    }

    public void setDurationEditable(Boolean durationEditable) {
        this.durationEditable = durationEditable;
    }

    public Boolean getStartEditable() {
        return startEditable;
    }

    public void setStartEditable(Boolean startEditable) {
        this.startEditable = startEditable;
    }

    public String getClassNames() {
        return classNames;
    }

    public void setClassNames(String classNames) {
        this.classNames = classNames;
    }

    public Boolean getInteractive() {
        return interactive;
    }

    public void setInteractive(Boolean interactive) {
        this.interactive = interactive;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}