package com.company.onboarding;

import com.company.onboarding.component.*;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.sys.registration.ComponentRegistration;
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder;
import io.jmix.flowui.xml.layout.loader.component.DatePickerLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// tag::component-registration-configuration[]
@Configuration
public class ComponentRegistrationConfiguration {
    // tag::date-picker-registration[]
    @Bean
    public ComponentRegistration datePicker() {
        return ComponentRegistrationBuilder.create(ExtendedDatePicker.class)
                .withComponentLoader("datePicker", DatePickerLoader.class)
                .replaceComponent(TypedDatePicker.class)
                .build();
    }
    // end::date-picker-registration[]
}
// end::component-registration-configuration[]
