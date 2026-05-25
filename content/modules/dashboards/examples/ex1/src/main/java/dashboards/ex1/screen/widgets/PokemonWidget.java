package dashboards.ex1.screen.widgets;

import dashboards.ex1.entity.Pet;
import io.jmix.core.DataManager;
import io.jmix.dashboards.model.Widget;
import io.jmix.dashboardsui.annotation.DashboardWidget;
import io.jmix.dashboardsui.annotation.WidgetParam;
import io.jmix.ui.WindowParam;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

// tag::with-custom-editor[]
@UiController("sample_PokemonWidget")
@UiDescriptor("pokemon-widget.xml")
@DashboardWidget(name = "Pokemon Widget", editFragmentId = "sample_PokemonWidgetEditor") // <1>
public class PokemonWidget extends ScreenFragment {

    @Autowired
    private InstanceContainer<Pet> petDc;

    @WindowParam
    @WidgetParam
    protected Pet pet; // <2>

    @Subscribe
    public void onInit(InitEvent event) {
        petDc.setItem(pet);
    }
}
// end::with-custom-editor[]