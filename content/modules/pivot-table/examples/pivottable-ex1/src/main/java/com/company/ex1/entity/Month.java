package com.company.ex1.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum Month implements EnumClass<String> {

    JANUARY("january"),
    FEBRUARY("february"),
    MARCH("march"),
    APRIL("april"),
    MAY("may"),
    JUNE("june"),
    JULY("july"),
    AUGUST("august"),
    SEPTEMBER("september"),
    OCTOBER("october"),
    NOVEMBER("november"),
    DECEMBER("december");

    private final String id;

    Month(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Month fromId(String id) {
        for (Month at : Month.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
