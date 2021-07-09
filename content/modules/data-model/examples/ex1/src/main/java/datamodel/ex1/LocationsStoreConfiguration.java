package datamodel.ex1;

import io.jmix.autoconfigure.data.JmixLiquibaseCreator;
import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import io.jmix.data.persistence.DbmsSpecifics;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

// tag::add-data-source[]
@Configuration
public class LocationsStoreConfiguration {

    @Bean
    @ConfigurationProperties("locations.datasource")
    DataSourceProperties locationsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "locations.datasource.hikari")
    DataSource locationsDataSource(@Qualifier("locationsDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean locationsEntityManagerFactory(
            @Qualifier("locationsDataSource") DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter,
            DbmsSpecifics dbmsSpecifics,
            JmixModules jmixModules,
            Resources resources) {
        return new JmixEntityManagerFactoryBean("locations", dataSource, jpaVendorAdapter, dbmsSpecifics, jmixModules, resources);
    }

    @Bean
    JpaTransactionManager locationsTransactionManager(@Qualifier("locationsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("locations", entityManagerFactory);
    }

    @Bean
    public SpringLiquibase locationsLiquibase(LiquibaseChangeLogProcessor processor, @Qualifier("locationsDataSource") DataSource dataSource) {
        return JmixLiquibaseCreator.create(dataSource, new LiquibaseProperties(), processor, "locations");
    }
}
// end::add-data-source[]
