package sample;

import io.jmix.autoconfigure.data.JmixLiquibase;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class FakeStoreConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "locations.datasource")
    DataSource locationsDataSource() {
        return DataSourceBuilder.create().build();
    }

    // tag::liquibase-props[]
    @Bean
    @ConfigurationProperties(prefix = "locations.liquibase")
    public LiquibaseProperties locationsLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase locationsLiquibase(LiquibaseChangeLogProcessor processor) {
        return JmixLiquibase.create(locationsDataSource(),
                locationsLiquibaseProperties(), processor, "locations");
    }
    // end::liquibase-props[]
}
