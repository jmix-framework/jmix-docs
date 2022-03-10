package ui.ex1.components.stepper;

import com.google.common.base.Strings;
import io.jmix.ui.xml.layout.loader.AbstractFieldLoader;

public class StepperFieldLoader extends AbstractFieldLoader<StepperField> { // <1>
    @Override
    public void createComponent() {
        resultComponent = factory.create(StepperField.NAME); // <2>
        loadId(resultComponent, element);
    }

    @Override
    public void loadComponent() {
        super.loadComponent();
        String incrementStr = element.attributeValue("step"); // <3>
        if (!Strings.isNullOrEmpty(incrementStr)) {
            resultComponent.setStep(Integer.parseInt(incrementStr));
        }
    }
}
