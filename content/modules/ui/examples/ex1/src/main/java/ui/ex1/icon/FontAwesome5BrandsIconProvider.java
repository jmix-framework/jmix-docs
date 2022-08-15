package ui.ex1.icon;

import com.google.common.base.Strings;
import com.vaadin.server.Resource;
import io.jmix.ui.icon.IconProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// tag::icon-provider[]
@Order(10)
@Component("sample_FontAwesome5BrandsIconProvider")
public class FontAwesome5BrandsIconProvider implements IconProvider {

    public static final String FONT_AWESOME_5_BRANDS_PREFIX = "font-awesome5-brands-icon:";
    private final Logger log = LoggerFactory.getLogger(FontAwesome5BrandsIconProvider.class);

    @Override
    public Resource getIconResource(String iconPath) {
        Resource resource = null;

        iconPath = iconPath.split(":")[1];

        try {
            resource = ((Resource) FontAwesome5Brands.class
                    .getDeclaredField(iconPath)
                    .get(null));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.warn("There is no icon with name {} in the FontAwesome5Brands icon set", iconPath);
        }

        return resource;
    }

    @Override
    public boolean canProvide(String iconPath) {
        return !Strings.isNullOrEmpty(iconPath)
                && iconPath.startsWith(FONT_AWESOME_5_BRANDS_PREFIX);
    }
}
// end::icon-provider[]