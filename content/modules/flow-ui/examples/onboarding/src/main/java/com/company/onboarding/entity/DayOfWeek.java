package com.company.onboarding.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum DayOfWeek implements EnumClass<String> {

    MONDAY("MON"),
    TUESDAY("TUE"),
    WEDNESDAY("WED"),
    THURSDAY("THU"),
    FRIDAY("FRI"),
    SATURDAY("SAT"),
    SUNDAY("SUN");

    private final String id;

    DayOfWeek(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DayOfWeek fromId(String id) {
        for (DayOfWeek at : DayOfWeek.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}