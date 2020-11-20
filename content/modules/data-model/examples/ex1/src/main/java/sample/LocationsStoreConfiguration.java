package sample;

import io.jmix.autoconfigure.data.JmixLiquibase;
import io.jmix.core.JmixModules;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.impl.PersistenceConfigProcessor;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import io.jmix.data.persistence.DbmsSpecifics;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
    @ConfigurationProperties(prefix = "locations.datasource")
    DataSource locationsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean locationsEntityManagerFactory(PersistenceConfigProcessor processor, JpaVendorAdapter jpaVendorAdapter, DbmsSpecifics dbmsSpecifics, JmixModules jmixModules) {
        return new JmixEntityManagerFactoryBean("locations", locationsDataSource(), processor, jpaVendorAdapter, dbmsSpecifics, jmixModules);
    }

    @Bean
    JpaTransactionManager locationsTransactionManager(@Qualifier("locationsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("locations", entityManagerFactory);
    }

    @Bean
    public SpringLiquibase locationsLiquibase(LiquibaseChangeLogProcessor processor) {
        return JmixLiquibase.create(locationsDataSource(), new LiquibaseProperties(), processor, "locations");
    }
}
// end::add-data-source[]
