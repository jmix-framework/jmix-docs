// tag::package[]
package modularity.sample.base;
// ...
// end::package[]

import io.jmix.core.annotation.JmixModule;
import modularity.sample.base.BaseConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(BaseConfiguration.class)
// tag::class[]
@JmixModule(id = "modularity.sample.base.test",
        dependsOn = BaseConfiguration.class)
public class BaseTestConfiguration {
// end::class[]

    @Bean
    @Primary
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }
}
