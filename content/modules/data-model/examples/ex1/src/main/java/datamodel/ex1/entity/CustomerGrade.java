package datamodel.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;

// tag::enum[]
public enum CustomerGrade implements EnumClass<String> { // <1>

    BRONZE("B"), // <2>
    GOLD("G"),
    PLATINUM("P");

    private String id;

    CustomerGrade(String value) {
        this.id = value;
    }

    public String getId() { // <3>
        return id;
    }

    @Nullable
    public static CustomerGrade fromId(String id) { // <3>
        for (CustomerGrade at : CustomerGrade.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
// end::enum[]
