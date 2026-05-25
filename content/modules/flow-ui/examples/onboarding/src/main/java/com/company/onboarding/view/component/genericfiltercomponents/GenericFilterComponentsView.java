package com.company.onboarding.view.component.genericfiltercomponents;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.genericfilter.configuration.DesignTimeConfiguration;
import io.jmix.flowui.component.jpqlfilter.JpqlFilter;
import io.jmix.flowui.component.logicalfilter.GroupFilter;
import io.jmix.flowui.component.logicalfilter.LogicalFilterComponent;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.component.propertyfilter.SingleFilterSupport;
import io.jmix.flowui.component.jpqlfilter.JpqlFilterSupport;
import io.jmix.flowui.model.DataLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "GenericFilterComponentsView", layout = MainView.class)
@ViewController("GenericFilterComponentsView")
@ViewDescriptor("generic-filter-components-view.xml")
public class GenericFilterComponentsView extends StandardView {

    // tag::UiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::UiComponents[]

    // tag::pfdtcGenericFilter[]
    @ViewComponent
    private GenericFilter pfdtcGenericFilter;

    // end::pfdtcGenericFilter[]


    // tag::SingleFilterSupport[]
    @Autowired
    private SingleFilterSupport singleFilterSupport;

    // end::SingleFilterSupport[]


    // tag::jfdtcGenericFilter[]
    @ViewComponent
    private GenericFilter jfdtcGenericFilter;

    // end::jfdtcGenericFilter[]

    // tag::jfdtcGenericFilterNoParameter[]
    @ViewComponent
    private GenericFilter jfdtcGenericFilterNoParameter;

    // end::jfdtcGenericFilterNoParameter[]

    // tag::JpqlFilterSupport[]
    @Autowired
    private JpqlFilterSupport jpqlFilterSupport;

    // end::JpqlFilterSupport[]

    // tag::gfdtcGenericFilter[]
    @ViewComponent
    private GenericFilter gfdtcGenericFilter;

    // end::gfdtcGenericFilter[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::programmaticPropertyFilter[]
        DesignTimeConfiguration javaDefaultConfigurationPF =
                pfdtcGenericFilter.addConfiguration("javaDefaultConfiguration",
                        "PropertyFilter configuration"); // <1>
        DataLoader dataLoaderPF = pfdtcGenericFilter.getDataLoader();

        PropertyFilter<Integer> agePropertyFilter =
                uiComponents.create(PropertyFilter.class); // <2>
        agePropertyFilter.setConditionModificationDelegated(true);
        agePropertyFilter.setDataLoader(dataLoaderPF);
        agePropertyFilter.setProperty("age");
        agePropertyFilter.setOperation(PropertyFilter.Operation.GREATER_OR_EQUAL);
        agePropertyFilter.setOperationEditable(true);
        agePropertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(
                agePropertyFilter.getProperty()));
        agePropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderPF.getContainer().getEntityMetaClass(),
                agePropertyFilter.getProperty(),
                agePropertyFilter.getOperation())); // <3>
        javaDefaultConfigurationPF.getRootLogicalFilterComponent()
                .add(agePropertyFilter); // <4>

        pfdtcGenericFilter.setCurrentConfiguration(javaDefaultConfigurationPF); // <5>
        // end::programmaticPropertyFilter[]

        // tag::programmaticJpqlFilter[]
        DesignTimeConfiguration javaDefaultConfigurationJF =
                jfdtcGenericFilter.addConfiguration("javaDefaultConfiguration", "JpqlFilter configuration"); // <1>
        DataLoader dataLoaderJF = jfdtcGenericFilter.getDataLoader();

        JpqlFilter<String> jpqlFilter =
                uiComponents.create(JpqlFilter.class); // <2>
        jpqlFilter.setConditionModificationDelegated(true);
        jpqlFilter.setDataLoader(dataLoaderJF);
        jpqlFilter.setCondition("c.name = ?", "join {E}.city c");
        jpqlFilter.setParameterClass(String.class);
        jpqlFilter.setLabel("City name is");
        jpqlFilter.setParameterName(jpqlFilterSupport.generateParameterName(
                String.valueOf(jpqlFilter.getId()),
                jpqlFilter.getParameterClass().getSimpleName()));
        jpqlFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderJF.getContainer().getEntityMetaClass(),
                jpqlFilter.hasInExpression(),
                jpqlFilter.getParameterClass())); // <3>
        javaDefaultConfigurationJF.getRootLogicalFilterComponent()
                .add(jpqlFilter); // <4>

        jfdtcGenericFilter.setCurrentConfiguration(javaDefaultConfigurationJF); // <5>
        // end::programmaticJpqlFilter[]

        // tag::programmaticJpqlFilterNoParameter[]
        DesignTimeConfiguration javaConfiguration = jfdtcGenericFilterNoParameter
                .addConfiguration("javaConfiguration", "No-parameter JpqlFilter configuration");
        DataLoader dataLoaderNoParam = jfdtcGenericFilterNoParameter.getDataLoader();

        JpqlFilter<Boolean> jpqlFilterNoParameter = uiComponents.create(JpqlFilter.class);
        jpqlFilterNoParameter.setConditionModificationDelegated(true);
        jpqlFilterNoParameter.setDataLoader(dataLoaderNoParam);
        jpqlFilterNoParameter.setCondition("c.name = 'London'", "join {E}.city c");
        jpqlFilterNoParameter.setParameterClass(Void.class);
        jpqlFilterNoParameter.setLabel("Show customers from London");
        jpqlFilterNoParameter.setParameterName(jpqlFilterSupport
                .generateParameterName(
                        String.valueOf(jpqlFilterNoParameter.getId()),
                        jpqlFilterNoParameter.getParameterClass().getSimpleName()));
        jpqlFilterNoParameter.setValueComponent(singleFilterSupport
                .generateValueComponent(dataLoaderNoParam.getContainer().getEntityMetaClass(),
                        jpqlFilterNoParameter.hasInExpression(),
                        jpqlFilterNoParameter.getParameterClass()));

        jpqlFilterNoParameter.setValue(true);
        javaConfiguration.setFilterComponentDefaultValue(
                jpqlFilterNoParameter.getParameterName(),
                jpqlFilterNoParameter.getValue());

        jfdtcGenericFilterNoParameter.setCurrentConfiguration(javaConfiguration);

        javaConfiguration.getRootLogicalFilterComponent()
                .add(jpqlFilterNoParameter);
        // end::programmaticJpqlFilterNoParameter[]




        // tag::programmaticGroupFilter[]
        DesignTimeConfiguration javaDefaultConfigurationGF =
                gfdtcGenericFilter.addConfiguration("javaDefaultConfiguration",
                        "GroupFilter configuration"); // <1>
        DataLoader dataLoaderGF = gfdtcGenericFilter.getDataLoader();

        GroupFilter groupFilter =
                uiComponents.create(GroupFilter.class); // <2>
        groupFilter.setConditionModificationDelegated(true);
        groupFilter.setDataLoader(dataLoaderGF);
        groupFilter.setOperation(LogicalFilterComponent.Operation.OR); // <3>

        PropertyFilter<Integer> rewardsPropertyFilter =
                uiComponents.create(PropertyFilter.class);
        rewardsPropertyFilter.setConditionModificationDelegated(true);
        rewardsPropertyFilter.setDataLoader(dataLoaderGF);
        rewardsPropertyFilter.setProperty("rewardPoints");
        rewardsPropertyFilter.setOperation(PropertyFilter.Operation.GREATER_OR_EQUAL);
        rewardsPropertyFilter.setOperationEditable(true);
        rewardsPropertyFilter.setParameterName(PropertyConditionUtils
                .generateParameterName(rewardsPropertyFilter.getProperty()));
        rewardsPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderGF.getContainer().getEntityMetaClass(),
                rewardsPropertyFilter.getProperty(),
                rewardsPropertyFilter.getOperation()));

        groupFilter.add(rewardsPropertyFilter); // <4>

        PropertyFilter<String> hobbyPropertyFilter =
                uiComponents.create(PropertyFilter.class);
        hobbyPropertyFilter.setConditionModificationDelegated(true);
        hobbyPropertyFilter.setDataLoader(dataLoaderGF);
        hobbyPropertyFilter.setProperty("hobby");
        hobbyPropertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
        hobbyPropertyFilter.setOperationEditable(true);
        hobbyPropertyFilter.setParameterName(PropertyConditionUtils
                .generateParameterName(hobbyPropertyFilter.getProperty()));
        hobbyPropertyFilter.setValueComponent(singleFilterSupport.generateValueComponent(
                dataLoaderGF.getContainer().getEntityMetaClass(),
                hobbyPropertyFilter.getProperty(),
                hobbyPropertyFilter.getOperation()));

        groupFilter.add(hobbyPropertyFilter); // <5>
        javaDefaultConfigurationGF.getRootLogicalFilterComponent()
                .add(groupFilter); // <6>
        // end::programmaticGroupFilter[]

        // tag::onInit[]
    }
    // end::onInit[]
}