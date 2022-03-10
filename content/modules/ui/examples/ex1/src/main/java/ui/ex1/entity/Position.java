package ui.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Position implements EnumClass<String> {

    CEO("Chief Executive Officer"),
    BA("Business Analyst"),
    PM("Project Manager"),
    DEV("Developer");

    private String id;

    Position(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Position fromId(String id) {
        for (Position at : Position.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}