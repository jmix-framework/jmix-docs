package com.company.jmixreports;

import com.company.jmixreports.app.ReportHistoryCleanJob;
import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class JmixreportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmixreportsApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
    // tag::quartz[]
    @Bean
    JobDetail reportHistoryCleanJob() {
        return JobBuilder.newJob()
                .ofType(ReportHistoryCleanJob.class)
                .storeDurably()
                .withIdentity("reportHistoryClean")
                .build();
    }

    @Bean
    Trigger reportHistoryCleanTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(reportHistoryCleanJob())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ?")) // <1>
                .build();
    }
    // end::quartz[]
}
