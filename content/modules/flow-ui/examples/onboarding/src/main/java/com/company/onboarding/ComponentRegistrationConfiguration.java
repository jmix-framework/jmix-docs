package com.company.onboarding;

import com.company.onboarding.component.Slider;
import com.company.onboarding.component.SliderLoader;
import com.company.onboarding.component.ThemeToggle;
import com.company.onboarding.component.ThemeToggleLoader;
import io.jmix.flowui.sys.registration.ComponentRegistration;
import io.jmix.flowui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// tag::component-registration-configuration[]
@Configuration
public class ComponentRegistrationConfiguration {

    // tag::slider-registration[]
    @Bean
    public ComponentRegistration slider() {
        return ComponentRegistrationBuilder.create(Slider.class)
                .withComponentLoader("slider", SliderLoader.class)
                .build();
    }
    // end::slider-registration[]

    // tag::theme-toggle-registration[]
    @Bean
    public ComponentRegistration themeToggle() {
        return ComponentRegistrationBuilder.create(ThemeToggle.class)
                .withComponentLoader("themeToggle", ThemeToggleLoader.class)
                .build();
    }
    // end::theme-toggle-registration[]
}
// end::component-registration-configuration[]