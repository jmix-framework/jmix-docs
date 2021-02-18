package ui.ex1.screen.component.entitysuggestionfield;

import io.jmix.core.DataManager;
import io.jmix.core.MetadataTools;
import io.jmix.ui.Actions;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.component.EntitySuggestionField;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UiController("entitySuggestionField-screen")
@UiDescriptor("entitysuggestionfield-screen.xml")
public class EntitySuggestionFieldScreen extends Screen {

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

    @Autowired
    private CollectionContainer<Country> countriesDc;

    @Autowired
    private EntitySuggestionField<Country> metaClassField;

    @Autowired
    private MetadataTools metadataTools;

    @Autowired
    private CollectionLoader<Country> countriesDl;

/*    @Subscribe
    public void onInit(InitEvent event) {
        countriesDl.load();
        List<Country> countries = new ArrayList<>(countriesDc.getItems());
        metaClassField.setSearchExecutor((searchString, searchParams) ->
                countries.stream()
                        .filter(country ->
                                StringUtils.containsIgnoreCase(metadataTools.getInstanceName(country), searchString))
                        .collect(Collectors.toList()));
    }*/

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

    @Subscribe
    public void onInit(InitEvent event) {
        entitySuggestionField.addAction(actions.create(EntityClearAction.class));
    }
    // end::add-action[]
}