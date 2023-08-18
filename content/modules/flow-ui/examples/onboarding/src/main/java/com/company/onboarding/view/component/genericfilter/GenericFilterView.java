package com.company.onboarding.view.component.genericfilter;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.flowui.view.*;

@Route(value = "GenericFilterView", layout = MainView.class)
@ViewController("GenericFilterView")
@ViewDescriptor("generic-filter-view.xml")
public class GenericFilterView extends StandardView {

    // tag::propertyFiltersPredicate[]
    @Install(to = "genericFilter", subject = "propertyFiltersPredicate")
    private boolean genericFilterPropertyFiltersPredicate(final MetaPropertyPath metaPropertyPath) {
        return !metaPropertyPath.getMetaProperty().getName().equals("hobby");
    }
    // end::propertyFiltersPredicate[]

}