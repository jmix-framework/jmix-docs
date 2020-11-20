package sample;

import io.jmix.core.security.CoreSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleDataModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleDataModelApplication.class, args);
    }

    // tag::main-data-source[]
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    // end::main-data-source[]

    @EnableWebSecurity
    static class SamplerSecurityConfiguration extends CoreSecurityConfiguration {
    }
}
