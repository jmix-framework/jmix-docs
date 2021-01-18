package email.ex1;

import io.jmix.core.security.CoreSecurityConfiguration;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleEmailApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @EnableWebSecurity
    static class SecurityConfiguration extends CoreSecurityConfiguration {
    }

    // tag::quartz[]
    @Bean
    JobDetail emailSendingJob() {
        return JobBuilder.newJob()
                .ofType(EmailSendingJob.class)
                .storeDurably()
                .withIdentity("emailSending")
                .build();
    }

    @Bean
    Trigger emailSendingTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(emailSendingJob())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")) // <1>
                .build();
    }
    // end::quartz[]
}
