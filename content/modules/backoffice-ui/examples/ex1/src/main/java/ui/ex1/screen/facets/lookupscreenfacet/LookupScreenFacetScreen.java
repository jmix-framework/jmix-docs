package ui.ex1.screen.facets.lookupscreenfacet;

import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Hobby;
import ui.ex1.entity.Level;

import java.util.Collection;
import java.util.stream.Collectors;

@UiController("sample_LookupScreenFacetScreen")
@UiDescriptor("lookup-screen-facet-screen.xml")
public class LookupScreenFacetScreen extends Screen {

    // tag::select-handler[]
    @Autowired
    private TextField<String> userField;

    @Install(to = "lookupScreen", subject = "selectHandler")
    private void lookupScreenSelectHandler(Collection<Customer> collection) {
        if(collection.iterator().hasNext()){
            userField.setValue(collection.iterator().next().getEmail());
        }
    }
    // end::select-handler[]
}