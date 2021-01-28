package dataaccess.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum CustomerGrade implements EnumClass<String> {

    BRONZE("B"),
    GOLD("G"),
    PLATINUM("P");

    private String id;

    CustomerGrade(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CustomerGrade fromId(String id) {
        for (CustomerGrade at : CustomerGrade.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}