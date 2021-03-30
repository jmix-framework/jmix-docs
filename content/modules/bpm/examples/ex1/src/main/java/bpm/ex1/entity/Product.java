package bpm.ex1.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Product implements EnumClass<String> {

    T_SHIRT("T-shirt"),
    JACKET("Jacket"),
    SHORTS("Shorts"),
    JEANS("Jeans"),
    SKIRT("Skirt");

    private String id;

    Product(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Product fromId(String id) {
        for (Product at : Product.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}