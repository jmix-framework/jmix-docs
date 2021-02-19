package ui.ex1.screen.component.entitysuggestionfield;

import io.jmix.core.DataManager;
import io.jmix.ui.Actions;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.EntitySuggestionField;
import io.jmix.ui.component.SuggestionFieldComponent;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UiController("entitySuggestionField-screen")
@UiDescriptor("entitysuggestionfield-screen.xml")
public class EntitySuggestionFieldScreen extends Screen {
    @Autowired
    private CollectionLoader<Country> countriesDl;

    // tag::arrow-down[]
    @Autowired
    private CollectionContainer<Country> countriesDc;

    @Autowired
    private EntitySuggestionField<Country> countryField;

    @Install(to = "countryField", subject = "arrowDownHandler")
    private void countryFieldArrowDownHandler(SuggestionFieldComponent.ArrowDownEvent arrowDownEvent) {
        countryField.showSuggestions(new ArrayList<>(countriesDc.getItems()));
    }
    // end::arrow-down[]

    // tag::search-executor[]
    @Autowired
    private DataManager dataManager;

    @Install(to = "entityField", subject = "searchExecutor")
    private List entityFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Country.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }
    // end::search-executor[]

    // tag::search-executor-metaclass[]
    @Install(to = "metaClassField", subject = "searchExecutor")
    private List<Country> metaClassFieldSearchExecutor(String searchString, Map<String, Object> searchParams) {
        return dataManager.load(Country.class)
                .query("e.name like ?1 order by e.name", "(?i)%" + searchString + "%")
                .list();
    }
    // end::search-executor-metaclass[]

    // tag::add-action[]
    @Autowired
    private EntitySuggestionField<Country> entitySuggestionField;

    @Autowired
    private Actions actions;

    // end::add-action[]
    // tag::init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::init-start[]
        // tag::add-action-2[]
        entitySuggestionField.addAction(actions.create(EntityClearAction.class));
        // end::add-action-2[]
        countriesDl.load();
        // tag::init-end[]
    }
    // end::init-end[]
}