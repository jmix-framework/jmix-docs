package ui.ex1.screen.facets.editorscreenfacet;

import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.User;

@UiController("sample_EditorScreenFacetScreen")
@UiDescriptor("editor-screen-facet-screen.xml")
public class EditorScreenFacetScreen extends Screen {

    // tag::entity-provider[]
    @Autowired
    private DataManager dataManager;

    @Install(to = "editorScreen", subject = "entityProvider")
    private User editorScreenEntityProvider() {
        User user = dataManager.create(User.class);
        user.setFirstName("John");
        user.setLastName("Doe");

        return user;
    }
    // end::entity-provider[]


    // tag::data-context[]
    @Autowired
    private DataContext dataContext;

    @Install(to = "editorScreen", subject = "parentDataContextProvider")
    private DataContext editorScreenParentDataContextProvider() {
        return dataContext;
    }
    // end::data-context[]

}
