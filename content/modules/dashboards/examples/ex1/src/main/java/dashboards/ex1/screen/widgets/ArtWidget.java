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

// tag::widget[]
@UiController("sample_ArtWidget")
@UiDescriptor("art-widget.xml")
@DashboardWidget(name = "Pokemon Art")
public class ArtWidget extends ScreenFragment {

    public static final String INIT_POKEMON = "Cubone"; // <1>

    @Autowired
    private InstanceContainer<Pet> petDc; // <2>

    @Autowired
    private DataManager dataManager;

    @WindowParam
    @WidgetParam
    protected Pet pet; // <3>

    @WindowParam
    protected Widget widget; // <4>

    @Subscribe
    public void onInit(InitEvent event) {
        if (pet == null || pet.getPicture() == null) { // <5>
            pet = dataManager.loadValue("select s from sample_Pet s where " +
                            "s.name = :name", Pet.class)
                    .parameter("name", INIT_POKEMON)
                    .one();
        }
        petDc.setItem(pet); // <6>
        widget.setCaption(pet.getName()); // <7>
    }
}
// end::widget[]