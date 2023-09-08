package com.company.onboarding.view.component.customcomponent;

import io.jmix.flowui.sys.registration.ComponentRegistration;
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomComponentConfiguration {

    @Bean
    public ComponentRegistration address() {
        return ComponentRegistrationBuilder.create(AddressComponent.class)
                .withComponentLoader("address", AddressComponentLoader.class)
                .build();
    }
}
