package ui.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Operation implements EnumClass<Integer> {

    CREATE(10),
    READ(20),
    UPDATE(30),
    DELETE(40);

    private Integer id;

    Operation(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Operation fromId(Integer id) {
        for (Operation at : Operation.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}