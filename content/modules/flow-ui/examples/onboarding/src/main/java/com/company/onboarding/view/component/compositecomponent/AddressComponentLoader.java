package com.company.onboarding.view.component.compositecomponent;

import com.company.onboarding.entity.Address;
import io.jmix.flowui.exception.GuiDevelopmentException;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;
import org.dom4j.Element;

// tag::address-component-loader[]
public class AddressComponentLoader extends AbstractComponentLoader<AddressComponent> {
    // end::address-component-loader[]

    // tag::create-component[]
    @Override
    protected AddressComponent createComponent() {
        return factory.create(AddressComponent.class);
    }
    // end::create-component[]

    // tag::load-component[]
    @Override
    public void loadComponent() {
        loadDataContainer(resultComponent, element);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void loadDataContainer(AddressComponent resultComponent, Element element) {
        String dataContainerIsNullErrorMessage = String.format(
                "%s doesn't have data binding. Set dataContainer attribute.",
                resultComponent.getClass().getSimpleName()
        );
        String containerId = loadString(element, "dataContainer")
                .orElseThrow(() -> new GuiDevelopmentException(dataContainerIsNullErrorMessage, context));

        InstanceContainer container = getComponentContext().getViewData().getContainer(containerId);
        if (!Address.class.isAssignableFrom(container.getEntityMetaClass().getJavaClass())) {
            String improperDataBindingMessage = String.format(
                    "%s have improper data binding. The value for the " +
                            "dataContainer attribute should be associated with the Address embeddable entity.",
                    resultComponent.getClass().getSimpleName());
            throw new GuiDevelopmentException(improperDataBindingMessage, context);
        }

        resultComponent.setDataContainer(container);
    }
    // end::load-component[]

    // tag::address-component-loader[]
}
// end::address-component-loader[]
