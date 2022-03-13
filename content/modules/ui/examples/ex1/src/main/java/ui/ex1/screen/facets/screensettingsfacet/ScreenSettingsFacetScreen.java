package ui.ex1.screen.facets.screensettingsfacet;

import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import io.jmix.ui.settings.facet.ScreenSettingsFacet;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Car;


@UiController("sample_ScreenSettingsFacetScreen")
@UiDescriptor("screen-settings-facet-screen.xml")
public class ScreenSettingsFacetScreen extends Screen {
    // tag::apply-settings-delegate[]
    @Autowired
    private ScreenSettingsFacet settingsFacet;
    @Autowired
    private GroupTable<Car> carsTable;

    @Install(to = "settingsFacet", subject = "applySettingsDelegate")
    private void settingsFacetApplySettingsDelegate(ScreenSettingsFacet.SettingsContext settingsContext) {
        settingsContext.getScreenSettings()
                .getBoolean("carsTable", "visibility")
                .ifPresent(visibility -> carsTable.setVisible(visibility));

        settingsFacet.applySettings();
    }
    // end::apply-settings-delegate[]
}