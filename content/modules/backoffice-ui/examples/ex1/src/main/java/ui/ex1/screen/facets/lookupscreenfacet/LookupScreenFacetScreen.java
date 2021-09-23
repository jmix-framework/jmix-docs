package ui.ex1.screen.facets.lookupscreenfacet;

import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

import java.util.Collection;

@UiController("sample_LookupScreenFacetScreen")
@UiDescriptor("lookup-screen-facet-screen.xml")
public class LookupScreenFacetScreen extends Screen {

    // tag::select-handler[]
    @Autowired
    private TextField<String> userField;

    @Install(to = "lookupScreen", subject = "selectHandler")
    private void lookupScreenSelectHandler(Collection<Customer> collection) {
        if (!collection.isEmpty()) {
            userField.setValue(collection.iterator().next().getEmail());
        }
    }
    // end::select-handler[]
}