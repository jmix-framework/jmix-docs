package com.company.onboarding.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum KanbanStatus implements EnumClass<String> {

    TODO("todo"),
    IN_PROGRESS("in-progress"),
    VERIFICATION("verification"),
    DONE("done");

    private final String id;

    KanbanStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static KanbanStatus fromId(String id) {
        for (KanbanStatus at : KanbanStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}