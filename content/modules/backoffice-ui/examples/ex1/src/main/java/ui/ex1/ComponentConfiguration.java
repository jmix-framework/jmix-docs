package ui.ex1;

import io.jmix.ui.sys.registration.ComponentRegistration;
import io.jmix.ui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ui.ex1.components.stepper.StepperField;
import ui.ex1.components.stepper.StepperFieldLoader;

// tag::component-configuration[]
@Configuration
public class ComponentConfiguration {

    // end::component-configuration[]
    // tag::stepper-field-registration[]
    @Bean
    public ComponentRegistration stepperField() { // <1>
        return ComponentRegistrationBuilder.create(StepperField.NAME)
                .withComponentClass(StepperField.class)
                .withComponentLoaderClass(StepperFieldLoader.class)
                .build();
    }
    // end::stepper-field-registration[]
    // tag::component-configuration[]
}
// end::component-configuration[]