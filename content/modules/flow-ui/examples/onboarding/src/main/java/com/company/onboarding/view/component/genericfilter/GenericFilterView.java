package com.company.onboarding.view.component.genericfilter;


import com.company.onboarding.entity.Customer;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.jpqlfilter.JpqlFilter;
import io.jmix.flowui.component.logicalfilter.GroupFilter;
import io.jmix.flowui.component.logicalfilter.LogicalFilterComponent;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "GenericFilterView", layout = MainView.class)
@ViewController("GenericFilterView")
@ViewDescriptor("generic-filter-view.xml")
public class GenericFilterView extends StandardView {

    // tag::UiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::UiComponents[]

    //tag::vbox[]
    @ViewComponent
    private VerticalLayout programmaticFilterBox;

    //end::vbox[]

    // tag::CollectionLoader[]
    @ViewComponent
    private CollectionLoader<Customer> customerDl;

    // end::CollectionLoader[]

    // tag::propertyFiltersPredicate[]
    @Install(to = "genericFilter", subject = "propertyFiltersPredicate")
    private boolean genericFilterPropertyFiltersPredicate(final MetaPropertyPath metaPropertyPath) {
        return !metaPropertyPath.getMetaProperty().getName().equals("hobby");
    }
    // end::propertyFiltersPredicate[]

    // tag::programmaticFilter[]
    @Subscribe
    public void onInit(final InitEvent event) {
        GenericFilter genericFilter = uiComponents.create(GenericFilter.class); // <1>
        genericFilter.setId("programmaticFilter");
        genericFilter.setDataLoader(customerDl);
        genericFilter.loadConfigurationsAndApplyDefault();
        programmaticFilterBox.add(genericFilter); // <2>

        PropertyFilter<Integer> agePropertyFilter = genericFilter.filterComponentBuilder() // <3>
                .<Integer>propertyFilter()
                .property("age")
                .operation(PropertyFilter.Operation.LESS_OR_EQUAL)
                .operationEditable(true)
                .build();

        genericFilter.runtimeConfigurationBuilder() // <4>
                .id("javaConfiguration")
                .name("Default configuration")
                .add(agePropertyFilter)
                .makeCurrent() // <5>
                .buildAndRegister(); // <6>
    }
    // end::programmaticFilter[]

    private void builderExamples(GenericFilter genericFilter) {
        // tag::propertyFilter[]
        PropertyFilter<Integer> ageFilter = genericFilter.filterComponentBuilder()
                .<Integer>propertyFilter()
                .property("age")
                .operation(PropertyFilter.Operation.GREATER_OR_EQUAL)
                .operationEditable(true)
                .build();
        // end::propertyFilter[]

        // tag::voidJpqlFilter[]
        JpqlFilter<Boolean> hasRewardPoints = genericFilter.filterComponentBuilder()
                .jpqlFilter()
                .where("{E}.rewardPoints > 0")
                .label("Has reward points")
                .defaultValue(true)
                .build();
        // end::voidJpqlFilter[]

        // tag::typedJpqlFilter[]
        JpqlFilter<Integer> minAge = genericFilter.filterComponentBuilder()
                .jpqlFilter(Integer.class)
                .where("{E}.age >= :minAge")
                .parameterName("minAge")
                .label("Minimum age")
                .build();
        // end::typedJpqlFilter[]

        // tag::groupFilter[]
        GroupFilter orGroup = genericFilter.filterComponentBuilder()
                .groupFilter()
                .operation(LogicalFilterComponent.Operation.OR)
                .addAll(hasRewardPoints, minAge)
                .build();
        // end::groupFilter[]

        // tag::runtimeConfiguration[]
        genericFilter.runtimeConfigurationBuilder()
                .id("dynamicConfiguration")
                .name("Dynamic configuration")
                .add(ageFilter, 18) // <1>
                .add(orGroup) // <2>
                .makeCurrent() // <3>
                .allowDeletion() // <4>
                .buildAndRegister();
        // end::runtimeConfiguration[]
    }

}
