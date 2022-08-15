package ui.ex1.icon;

import io.jmix.ui.icon.Icons;
// tag::override-icon[]
public enum NewIcon implements Icons.Icon {

    OK("classpath:/icon/custom-ok.png");
    // end::override-icon[]
    private String source;

    NewIcon(String source) {
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
    // tag::override-icon[]
}
// end::override-icon[]