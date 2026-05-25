package com.company.onboarding.component;

// tag::slider-loader[]
import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;

public class SliderLoader extends AbstractComponentLoader<Slider> {

    @Override
    protected Slider createComponent() {
        return factory.create(Slider.class);
    }

    @Override
    public void loadComponent() {
        loadInteger(element, "min", resultComponent::setMin); // <1>
        loadInteger(element, "max", resultComponent::setMax);

        componentLoader().loadSizeAttributes(resultComponent, element); // <2>
    }
}
// end::slider-loader[]
