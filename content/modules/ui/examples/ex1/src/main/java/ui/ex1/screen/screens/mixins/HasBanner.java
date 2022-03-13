package ui.ex1.screen.screens.mixins;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Extensions;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.theme.ThemeClassNames;
import org.springframework.context.ApplicationContext;

// tag::has-banner[]
public interface HasBanner {

    @Subscribe
    default void initBanner(Screen.InitEvent event) {

        ApplicationContext applicationContext = Extensions.getApplicationContext(event.getSource()); // <1>
        UiComponents uiComponents = applicationContext.getBean(UiComponents.class); //  <2>

        Label<String> banner = uiComponents.create(Label.TYPE_STRING); // <3>
        banner.setStyleName(ThemeClassNames.LABEL_H2);
        banner.setValue("Hello, world!");

        event.getSource().getWindow().add(banner, 0); // <4>
    }
}
// end::has-banner[]