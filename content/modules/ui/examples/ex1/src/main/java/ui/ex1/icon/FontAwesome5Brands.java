package ui.ex1.icon;

import com.vaadin.server.FontIcon;
import com.vaadin.server.GenericFontIcon;

// tag::new-font-icon[]
public enum FontAwesome5Brands implements FontIcon {

    JAVA(0XF4E4);

    public static final String FONT_FAMILY = "FontAwesome5Brands";
    private final int codepoint;

    FontAwesome5Brands(int codepoint) {
        this.codepoint = codepoint;
    }


    @Override
    public String getFontFamily() {
        return FONT_FAMILY;
    }

    @Override
    public int getCodepoint() {
        return codepoint;
    }

    @Override
    public String getHtml() {
        return GenericFontIcon.getHtml(FONT_FAMILY, codepoint);
    }

    @Override
    public String getMIMEType() {
        throw new UnsupportedOperationException(FontIcon.class.getSimpleName()
                + " should not be used where a MIME type is needed.");
    }

    public static FontAwesome5Brands fromCodepoint(final int codepoint) {
        for (FontAwesome5Brands f : values()) {
            if (f.getCodepoint() == codepoint) {
                return f;
            }
        }
        throw new IllegalArgumentException(
                "Codepoint " + codepoint + " not found in FontAwesome 5");
    }
}
// end::new-font-icon[]