package ui.ex1.icon;

import io.jmix.ui.icon.Icons;
// tag::new-font-icon-set[]
public enum FontAwesome5Icon implements Icons.Icon {

    JAVA("font-awesome5-brands-icon:JAVA");

    protected String source;

    FontAwesome5Icon(String source) {
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
// end::new-font-icon-set[]