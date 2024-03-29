package dataaccess.ex1;

import io.jmix.core.repository.EnableJmixDataRepositories;
import io.jmix.core.security.CoreSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Qualifier;
import javax.sql.DataSource;

// tag::data-repositories[]
@SpringBootApplication
@EnableJmixDataRepositories
public class SampleDataAccessApplication {
// end::data-repositories[]

    public static void main(String[] args) {
        SpringApplication.run(SampleDataAccessApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties("main.datasource.hikari")
    DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @EnableWebSecurity
    static class SecurityConfiguration extends CoreSecurityConfiguration {
    }

    // tag::transaction-template[]
    @Bean
    @Primary
    TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
    // end::transaction-template[]

}
