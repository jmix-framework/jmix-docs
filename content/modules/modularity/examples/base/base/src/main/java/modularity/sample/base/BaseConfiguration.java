// tag::package[]
package modularity.sample.base;
// ...
// end::package[]

import io.jmix.core.annotation.JmixModule;
import io.jmix.core.impl.scanning.AnnotationScanMetadataReaderFactory;
import io.jmix.eclipselink.EclipselinkConfiguration;
import io.jmix.ui.UiConfiguration;
import io.jmix.ui.sys.UiControllersConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;

@Configuration
@ComponentScan
@ConfigurationPropertiesScan
// tag::jmix-module[]
@JmixModule(dependsOn = {EclipselinkConfiguration.class, UiConfiguration.class})
// end::jmix-module[]
// tag::property-source[]
@PropertySource(name = "modularity.sample.base",
        value = "classpath:/modularity/sample/base/module.properties")
// end::property-source[]
// tag::class[]
public class BaseConfiguration {
// end::class[]

    @Bean("base_BaseUiControllers")
    public UiControllersConfiguration screens(ApplicationContext applicationContext,
                                              AnnotationScanMetadataReaderFactory metadataReaderFactory) {
        UiControllersConfiguration uiControllers
                = new UiControllersConfiguration(applicationContext, metadataReaderFactory);
        uiControllers.setBasePackages(Collections.singletonList("modularity.sample.base"));
        return uiControllers;
    }
}
