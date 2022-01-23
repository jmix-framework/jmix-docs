package email.ex1;

import com.google.common.base.Strings;
import email.ex1.service.MyCustomEmailCleaningJob;
import email.ex1.service.MyCustomEmailSendingJob;
import io.jmix.autoconfigure.email.job.EmailCleaningJob;
import io.jmix.core.security.CoreSecurityConfiguration;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleEmailApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SampleEmailApplication.class, args);
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
        LoggerFactory.getLogger(SampleEmailApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }

    // tag::quartz[]
    @Bean
    JobDetail myCustomEmailSendingJob() {
        return JobBuilder.newJob()
                .ofType(MyCustomEmailSendingJob.class)
                .storeDurably()
                .withIdentity("emailSending")
                .build();
    }

    @Bean
    Trigger myCustomEmailSendingTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myCustomEmailSendingJob())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("* 0/2 * * * ?"))
                .build();
    }
    // end::quartz[]
    // tag::quartz-clean[]
    @Bean
    JobDetail myCustomEmailCleaningJob() {
        return JobBuilder.newJob()
                .ofType(MyCustomEmailCleaningJob.class)
                .storeDurably()
                .withIdentity("emailCleaning")
                .build();
    }

    @Bean
    Trigger MyCustomEmailCleaningTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myCustomEmailCleaningJob())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1/2 * ? "))
                .build();
    }
    // end::quartz-clean[]
}
