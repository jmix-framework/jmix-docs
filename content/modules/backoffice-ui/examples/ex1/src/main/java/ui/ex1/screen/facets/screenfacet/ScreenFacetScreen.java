package ui.ex1.screen.facets.screenfacet;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.ScreenFacet;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@UiController("sample_ScreenFacetScreen")
@UiDescriptor("screen-facet-screen.xml")
public class ScreenFacetScreen extends Screen {

    // tag::options-provider[]
    @Install(to = "propScreenFacet", subject = "optionsProvider")
    private ScreenOptions propScreenFacetOptionsProvider() {
        return new MapScreenOptions(new HashMap<String, Object>() {
            {
                put("num", 55);
            }
        });
    }
    // end::options-provider[]

    // tag::show[]
    @Autowired
    private ScreenFacet<Screen> screenFacet;

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        screenFacet.show();
    }
    // end::show[]
}