package ui.ex1.screen.component.filter;

import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.component.filter.configuration.DesignTimeConfiguration;
import io.jmix.ui.component.jpqlfilter.JpqlFilterSupport;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.*;

@UiController("filterScreen")
@UiDescriptor("filter-screen.xml")
public class FilterScreen extends Screen {
    // tag::inject-ui-components[]
    @Autowired
    protected UiComponents uiComponents;

    // end::inject-ui-components[]
    // tag::inject-customers-dl[]
    @Autowired
    protected CollectionLoader<Customer> customersDl;

    // end::inject-customers-dl[]
    // tag::inject-single-filter-support[]
    @Autowired
    protected SingleFilterSupport singleFilterSupport;
    // tag::inject-pfdtc-filter[]
    @Autowired
    protected Filter pfdtcFilter;
    // end::inject-pfdtc-filter[]
    // tag::inject-jfdtc-filter[]
    @Autowired
    protected Filter jfdtcFilter;
    // end::inject-jfdtc-filter[]
    // tag::inject-jpql-filter-support[]
    @Autowired
    protected JpqlFilterSupport jpqlFilterSupport;
    // end::inject-jpql-filter-support[]
    // tag::inject-gfdtc-filter[]
    @Autowired
    protected Filter gfdtcFilter;
    // end::inject-single-filter-support[]
    @Autowired
    private Notifications notifications;

    // end::inject-gfdtc-filter[]
    // tag::inject-parameterless-filter[]
    @Autowired
    private Filter parameterlessFilter;

    // end::inject-parameterless-filter[]

    // tag::configuration-change-event[]
    @Subscribe("filter")
    public void onFilterConfigurationChange(Filter.ConfigurationChangeEvent event) {
        notifications.create()
                .withCaption("Before: " + event.getPreviousConfiguration().getName() +
                        ". After: " + event.getNewConfiguration().getName())
                .show();
    }

    // end::configuration-change-event[]
    // tag::expanded-state-change-event[]
    @Subscribe("filter")
    public void onFilterExpandedStateChange(Collapsable.ExpandedStateChangeEvent event) {
        notifications.create()
                .withCaption("Expanded: " + event.isExpanded())
                .show();
    }
    // end::expanded-state-change-event[]

    // tag::properties-filter-predicate[]
    @Install(to = "filter", subject = "propertiesFilterPredicate")
    private boolean filterPropertiesFilterPredicate(MetaPropertyPath metaPropertyPath) {
        return !metaPropertyPath.getMetaProperty().getName().equals("hobby");
    }


    // end::properties-filter-predicate[]
    // tag::on-after-init-start[]
    @Autowired
    private VBoxLayout programmaticFilterContainer;

    @Subscribe
    protected void onAfterInit(AfterInitEvent event) {
        // end::on-after-init-start[]
        // tag::filter-creating[]
        Filter filter = uiComponents.create(Filter.class); // <1>
        filter.setId("programmaticFilter");
        filter.setDataLoader(customersDl);
        filter.loadConfigurationsAndApplyDefault();

        DesignTimeConfiguration javaDefaultConfiguration =
                filter.addConfiguration("javaDefaultConfiguration",
                        "Default configuration"); // <2>

        PropertyFilter<Integer> agePropertyFilter =
                uiComponents.create(PropertyFilter.class); // <3>

        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(customersDl);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.LESS_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                customersDl.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation())); // <4>

        javaDefaultConfiguration.getRootLogicalFilterComponent().add(agePropertyFilter); // <5>
        filter.setCurrentConfiguration(javaDefaultConfiguration); // <6>

        programmaticFilterContainer.add(filter); // <7>
        // end::filter-creating[]
        // tag::on-after-init-end[]
    }

    // end::on-after-init-end[]
    // tag::on-init-start[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::on-init-start[]
        // tag::property-filter-design-time[]
        DesignTimeConfiguration javaDefaultConfigurationPF =
                pfdtcFilter.addConfiguration("javaDefaultConfiguration",
                        "Default configuration"); // <1>
        DataLoader dataLoaderPF = pfdtcFilter.getDataLoader();

        PropertyFilter<City> cityPropertyFilter =
                uiComponents.create(PropertyFilter.NAME); // <2>
        cityPropertyFilter.setConditionModificationDelegated(true);
        cityPropertyFilter.setDataLoader(dataLoaderPF);
        cityPropertyFilter.setProperty("city");
        cityPropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        cityPropertyFilter.setOperationEditable(true);
        cityPropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                cityPropertyFilter.getProperty()));
        cityPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderPF.getContainer().getEntityMetaClass(),
                cityPropertyFilter.getProperty(),
                cityPropertyFilter.getOperation())); // <3>
        javaDefaultConfigurationPF.getRootLogicalFilterComponent()
                .add(cityPropertyFilter); // <4>

        PropertyFilter<Level> levelPropertyFilter =
                uiComponents.create(PropertyFilter.NAME);
        levelPropertyFilter.setConditionModificationDelegated(true);
        levelPropertyFilter.setDataLoader(dataLoaderPF);
        levelPropertyFilter.setProperty("level");
        levelPropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        levelPropertyFilter.setOperationEditable(true);
        levelPropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                levelPropertyFilter.getProperty()));
        levelPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderPF.getContainer().getEntityMetaClass(),
                levelPropertyFilter.getProperty(),
                levelPropertyFilter.getOperation()
        ));
        javaDefaultConfigurationPF.getRootLogicalFilterComponent().add(levelPropertyFilter);

        pfdtcFilter.setCurrentConfiguration(javaDefaultConfigurationPF); // <5>
        // end::property-filter-design-time[]
        // tag::jpql-filter-design-time[]
        DesignTimeConfiguration javaDefaultConfigurationJF =
                jfdtcFilter.addConfiguration("javaDefaultConfiguration",
                        "Default configuration"); // <1>
        DataLoader dataLoaderJF = jfdtcFilter.getDataLoader();

        JpqlFilter<Brand> jpqlFilter =
                uiComponents.create(JpqlFilter.NAME); // <2>
        jpqlFilter.setFrame(getWindow());
        jpqlFilter.setConditionModificationDelegated(true);
        jpqlFilter.setDataLoader(dataLoaderJF);
        jpqlFilter.setCondition("i.id = ?", "join {E}.favouriteBrands i");
        jpqlFilter.setParameterClass(Brand.class);
        jpqlFilter.setCaption("Select the brand");
        jpqlFilter.setParameterName(jpqlFilterSupport.generateParameterName(
                jpqlFilter.getId(),
                jpqlFilter.getParameterClass().getSimpleName()));
        jpqlFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderJF.getContainer().getEntityMetaClass(),
                jpqlFilter.hasInExpression(),
                jpqlFilter.getParameterClass())); // <3>

        javaDefaultConfigurationJF.getRootLogicalFilterComponent()
                .add(jpqlFilter); // <4>
        jfdtcFilter.setCurrentConfiguration(javaDefaultConfigurationJF); // <5>
        // end::jpql-filter-design-time[]
        // tag::group-filter-design-time[]
        DesignTimeConfiguration javaDefaultConfigurationGF =
                gfdtcFilter.addConfiguration("javaDefaultConfiguration",
                        "Default configuration"); // <1>
        DataLoader dataLoaderGF = gfdtcFilter.getDataLoader();

        GroupFilter groupFilter =
                uiComponents.create(GroupFilter.NAME); // <2>
        groupFilter.setConditionModificationDelegated(true);
        groupFilter.setDataLoader(dataLoaderGF);
        groupFilter.setOperation(LogicalFilterComponent.Operation.OR); // <3>

        PropertyFilter<Integer> pointsPropertyFilter =
                uiComponents.create(PropertyFilter.NAME); // <4>
        pointsPropertyFilter.setConditionModificationDelegated(true);
        pointsPropertyFilter.setDataLoader(dataLoaderGF);
        pointsPropertyFilter.setProperty("rewardPoints");
        pointsPropertyFilter.setOperation(PropertyFilter.Operation.GREATER_OR_EQUAL);
        pointsPropertyFilter.setOperationEditable(true);
        pointsPropertyFilter.setParameterName(PropertyConditionUtils
                .generateParameterName(pointsPropertyFilter.getProperty()));
        pointsPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderGF.getContainer().getEntityMetaClass(),
                pointsPropertyFilter.getProperty(),
                pointsPropertyFilter.getOperation()));

        groupFilter.add(pointsPropertyFilter); // <5>

        PropertyFilter<Hobby> hobbyPropertyFilter =
                uiComponents.create(PropertyFilter.NAME);
        hobbyPropertyFilter.setConditionModificationDelegated(true);
        hobbyPropertyFilter.setDataLoader(dataLoaderGF);
        hobbyPropertyFilter.setProperty("hobby");
        hobbyPropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        hobbyPropertyFilter.setOperationEditable(true);
        hobbyPropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                hobbyPropertyFilter.getProperty()));
        hobbyPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderGF.getContainer().getEntityMetaClass(),
                hobbyPropertyFilter.getProperty(),
                hobbyPropertyFilter.getOperation()
        ));
        groupFilter.add(hobbyPropertyFilter); // <6>
        javaDefaultConfigurationGF.getRootLogicalFilterComponent().add(groupFilter); // <7>

        PropertyFilter<Integer> agePropertyFilter =
                uiComponents.create(PropertyFilter.NAME);
        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(dataLoaderGF);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.GREATER_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderGF.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation()
        ));

        javaDefaultConfigurationGF.getRootLogicalFilterComponent().add(agePropertyFilter);

        pointsPropertyFilter.setValue(1000);
        javaDefaultConfigurationGF.setFilterComponentDefaultValue(
                pointsPropertyFilter.getParameterName(), 1000); // <8>

        hobbyPropertyFilter.setValue(Hobby.FISHING);
        javaDefaultConfigurationGF.setFilterComponentDefaultValue(
                hobbyPropertyFilter.getParameterName(), Hobby.FISHING);

        agePropertyFilter.setValue(30);
        javaDefaultConfigurationGF.setFilterComponentDefaultValue(
                agePropertyFilter.getParameterName(), 30);
        // end::group-filter-design-time[]

        // tag::parameterless-filter-design-time[]
        DesignTimeConfiguration javaConfiguration = parameterlessFilter
                .addConfiguration("javaConfiguration", "Java configuration");
        DataLoader dataLoader = parameterlessFilter.getDataLoader();

        JpqlFilter<Boolean> jpqlFilterNoParams = uiComponents.create(JpqlFilter.NAME);
        jpqlFilterNoParams.setFrame(getWindow());
        jpqlFilterNoParams.setConditionModificationDelegated(true);
        jpqlFilterNoParams.setDataLoader(dataLoader);
        jpqlFilterNoParams.setCondition("{E}.age > 21", null);
        jpqlFilterNoParams.setParameterClass(Void.class);
        jpqlFilterNoParams.setCaption("Customer's age > 21");
        jpqlFilterNoParams.setParameterName(jpqlFilterSupport
                .generateParameterName(jpqlFilterNoParams.getId(),
                jpqlFilterNoParams.getParameterClass().getSimpleName()));
        jpqlFilterNoParams.setValueComponent(singleFilterSupport
                .generateValueComponent(dataLoader.getContainer().getEntityMetaClass(),
                jpqlFilterNoParams.hasInExpression(),
                jpqlFilterNoParams.getParameterClass()
        ));

        jpqlFilterNoParams.setValue(true);
        javaConfiguration.setFilterComponentDefaultValue(
                jpqlFilterNoParams.getParameterName(),
                jpqlFilterNoParams.getValue());

        javaConfiguration.getRootLogicalFilterComponent().add(jpqlFilterNoParams);

        jpqlFilterNoParams.setValue(true);
        javaConfiguration.setFilterComponentDefaultValue(
                jpqlFilterNoParams.getParameterName(),
                jpqlFilterNoParams.getValue());

        javaConfiguration.getRootLogicalFilterComponent().add(jpqlFilterNoParams);
        // end::parameterless-filter-design-time[]

        // tag::on-init-end[]
    }
    // end::on-init-end[]
}