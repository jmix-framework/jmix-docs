package files.ex1;

import io.jmix.core.FileStorage;
import io.jmix.localfs.LocalFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class SampleFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleFilesApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    // tag::multiple-fs[]
    @Bean
    FileStorage myFileStorage() {
        return new LocalFileStorage("myfs", "/var/tmp/myfs");
    }
    // end::multiple-fs[]
}
