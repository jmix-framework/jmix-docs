package grapesjs.ex1;

import com.google.common.base.Strings;
import grapesjs.ex1.app.BlocksRegister;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleGrapesJSApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SampleGrapesJSApplication.class, args);
    }

    @EventListener
    public void initReports(ApplicationStartedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        BlocksRegister blocksRegister = applicationContext.getBean(BlocksRegister.class);
        blocksRegister.registerBlock();
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
        LoggerFactory.getLogger(SampleGrapesJSApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }
}
