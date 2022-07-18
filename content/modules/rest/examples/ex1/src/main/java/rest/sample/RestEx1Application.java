package rest.sample;

import com.google.common.base.Strings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
public class RestEx1Application {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(RestEx1Application.class, args);
    }

    @Bean
    @Primary
    BuildProperties buildProperties() {
        Properties entries = new Properties();
        entries.setProperty("version", "1.0");
        entries.setProperty("name", "sample_rest");
        return new BuildProperties(entries);
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

    @EventListener
    public void printApplicationUrl(ApplicationStartedEvent event) {
        LoggerFactory.getLogger(RestEx1Application.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }

    // tag::custom-cors[]
    @Bean
    public WebSecurityConfigurerAdapter mySecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                        .requestMatchers(requestMatchers ->
                                requestMatchers.antMatchers("/myapi/hello")
                        )
                        .cors(Customizer.withDefaults())
                        .authorizeRequests(authorize ->
                                authorize.anyRequest().permitAll()
                        );
            }
        };
    }
    // end::custom-cors[]
}
