package com.company.notificationsex1;

import com.google.common.base.Strings;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import io.jmix.notifications.NotificationType;
import io.jmix.notifications.NotificationTypesRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Push
@Theme(value = "notifications-ex1")
@PWA(name = "Notifications Ex1", shortName = "Notifications Ex1")
@SpringBootApplication
public class NotificationsEx1Application implements AppShellConfigurator {

    @Autowired
    private Environment environment;

    // tag::notification-type[]
    @Autowired
    private NotificationTypesRepository notificationTypesRepository;

    // end::notification-type[]

    public static void main(String[] args) {
        SpringApplication.run(NotificationsEx1Application.class, args);
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
    DataSource dataSource(final DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // tag::notification-type[]
    @EventListener
    public void onApplicationContextRefreshed(final ContextRefreshedEvent event) {
        notificationTypesRepository.registerTypes(
                new NotificationType("info", "INFO_CIRCLE"), // <1>
                new NotificationType("warn", "WARNING")
        );
    }
    // end::notification-type[]

    @EventListener
    public void printApplicationUrl(final ApplicationStartedEvent event) {
        LoggerFactory.getLogger(NotificationsEx1Application.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }
}
