package dashboards.ex1.screen.widgets;

import dashboards.ex1.entity.Pet;
import io.jmix.dashboardsui.annotation.WidgetParam;
import io.jmix.ui.WindowParam;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom-editor[]
@UiController("sample_PokemonWidgetEditor")
@UiDescriptor("pokemon-widget-editor.xml")
public class PokemonWidgetEditor extends ScreenFragment {

    @Autowired
    private EntityPicker<Pet> pokemonPicker;

    @WindowParam
    @WidgetParam
    protected Pet pet; // <1>

    @Subscribe("pokemonPicker") // <2>
    public void onPokemonPickerValueChange(HasValue.ValueChangeEvent<String> event) {
        pet=pokemonPicker.getValue(); // <3>
    }

}
// end::custom-editor[]