package ui.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Level implements EnumClass<String> {

    SILVER("slvr"),
    GOLD("gld"),
    PLATINUM("plnm"),
    DIAMOND("dmnd");


    private String id;

    Level(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Level fromId(String id) {
        for (Level at : Level.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}