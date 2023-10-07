package com.company.onboarding.view.component.genericfilter;


import com.company.onboarding.entity.Customer;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.genericfilter.configuration.DesignTimeConfiguration;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.component.propertyfilter.SingleFilterSupport;
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

    // tag::SingleFilterSupport[]
    @Autowired
    private SingleFilterSupport singleFilterSupport;

    // end::SingleFilterSupport[]

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

        DesignTimeConfiguration javaDefaultConfiguration =
                genericFilter.addConfiguration("javaDefaultConfiguration",
                        "Default configuration"); // <2>

        PropertyFilter<Integer> agePropertyFilter =
                uiComponents.create(PropertyFilter.class); // <3>

        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(customerDl);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.LESS_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setParameterName(PropertyConditionUtils
                .generateParameterName(agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                customerDl.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation())); // <4>

        javaDefaultConfiguration.getRootLogicalFilterComponent().add(agePropertyFilter); // <5>
        genericFilter.setCurrentConfiguration(javaDefaultConfiguration); // <6>

        programmaticFilterBox.add(genericFilter); // <7>
    }
    // end::programmaticFilter[]

}