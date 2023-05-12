package testing.ex1;

import io.jmix.core.security.CoreSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleTestingApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
