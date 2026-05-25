package search.ex1;

import com.google.common.base.Strings;
import io.jmix.autoconfigure.search.job.IndexingQueueProcessingJob;
import org.quartz.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleSearchApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(SampleSearchApplication.class, args);
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
		LoggerFactory.getLogger(SampleSearchApplication.class).info("Application started at "
				+ "http://localhost:"
				+ environment.getProperty("local.server.port")
				+ Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
	}

	// tag::beans[]
	@Bean
	JobDetail myCustomIndexingQueueProcessingJob() {
		return JobBuilder.newJob()
				.ofType(IndexingQueueProcessingJob.class)
				.storeDurably()
				.withIdentity("MyCustomIndexingQueueProcessing")
				.build();
	}

	@Bean
	Trigger myCustomIndexingQueueProcessingTrigger() {
		return TriggerBuilder.newTrigger()
				.forJob(myCustomIndexingQueueProcessingJob())
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
				.build();
	}
	// end::beans[]
}
