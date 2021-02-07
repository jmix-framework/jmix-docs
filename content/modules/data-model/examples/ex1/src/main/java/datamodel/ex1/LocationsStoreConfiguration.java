package datamodel.ex1;

import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.impl.liquibase.JmixLiquibase;
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
    LocalContainerEntityManagerFactoryBean locationsEntityManagerFactory(
            JpaVendorAdapter jpaVendorAdapter, DbmsSpecifics dbmsSpecifics, JmixModules jmixModules, Resources resources) {
        return new JmixEntityManagerFactoryBean(
                "locations", locationsDataSource(), jpaVendorAdapter, dbmsSpecifics, jmixModules, resources);
    }

    @Bean
    JpaTransactionManager locationsTransactionManager(
            @Qualifier("locationsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("locations", entityManagerFactory);
    }

    // commented out until https://github.com/Haulmont/jmix-data/issues/20#issuecomment-774607159 is resolved
//    @Bean
//    public SpringLiquibase locationsLiquibase(LiquibaseChangeLogProcessor processor) {
//        return JmixLiquibase.create(locationsDataSource(), new LiquibaseProperties(), processor, "locations");
//    }
}
// end::add-data-source[]
