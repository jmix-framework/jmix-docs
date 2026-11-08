package com.company.demo.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;

public enum SalesChannel implements EnumClass<String> {

    WEB("W"),
    STORE("S");

    private final String id;

    SalesChannel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SalesChannel fromId(String id) {
        for (SalesChannel at : SalesChannel.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
