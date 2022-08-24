package ui.ex1.icon;

import io.jmix.ui.icon.Icons;
// tag::icon-set[]
public enum MyIcon implements Icons.Icon {

    EYE("classpath:/icon/eye.png"), // <1>
    PENGUIN("classpath:/icon/penguin.png"); // <2>

    private String source;

    MyIcon(String source) {
        this.source = source;
    }

    @Override
    public String source() {
        return source;
    }

    @Override
    public String iconName() {
        return name();
    }
}
// end::icon-set[]