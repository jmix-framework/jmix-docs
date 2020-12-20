package sample.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;

public enum Hobby implements EnumClass<String> {

    READING("rdng"),
    FAMILY("fml"),
    MOVIES("mvs"),
    FISHING("fshg"),
    COMPUTER("cmpt"),
    GARDENING("grdg"),
    WALKING("wlkg"),
    MUSIC("msc"),
    SPORT("strt"),
    SHOPPING("shpg"),
    TRAVELING("trvl"),
    COOKING("ckng"),
    TV("tv");

    private String id;

    Hobby(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Hobby fromId(String id) {
        for (Hobby at : Hobby.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
