package ui.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum EducationalStage implements EnumClass<String> {

    NURSERY("Nursery School"),
    ELEMENTARY("Elementary School"),
    JUNIOR("Junior High School"),
    HIGH("Senior High School"),
    BACHELORS("Bachelors"),
    MASTERS("Masters"),
    DOCTORATE("Doctorate"),
    NO("No Education");

    private String id;

    EducationalStage(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EducationalStage fromId(String id) {
        for (EducationalStage at : EducationalStage.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}