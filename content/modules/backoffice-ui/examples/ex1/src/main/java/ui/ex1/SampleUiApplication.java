package ui.ex1;

import io.jmix.ui.sys.registration.ComponentRegistration;
import io.jmix.ui.sys.registration.ComponentRegistrationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ui.ex1.components.stepper.StepperField;
import ui.ex1.components.stepper.StepperFieldLoader;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleUiApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // tag::stepper-field-registration[]
    @Bean
    public ComponentRegistration stepperField() {
        return ComponentRegistrationBuilder.create(StepperField.NAME)
                .withComponentClass(StepperField.class)
                .withComponentLoaderClass(StepperFieldLoader.class)
                .build();
    }
    // end::stepper-field-registration[]
}
