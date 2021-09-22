package ldap.ex1;

import com.google.common.base.Strings;
import io.jmix.core.security.CompositeUserRepository;
import io.jmix.core.security.UserRepository;
import io.jmix.ldap.LdapProperties;
import io.jmix.ldap.userdetails.JmixLdapGrantedAuthoritiesMapper;
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
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LdapSampleApplication {

    // tag::ldap-properties[]
    @Autowired
    private LdapProperties ldapProperties;

    // end::ldap-properties[]

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(LdapSampleApplication.class, args);
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
        LoggerFactory.getLogger(LdapSampleApplication.class).info("Application started at "
                + "http://localhost:"
                + environment.getProperty("local.server.port")
                + Strings.nullToEmpty(environment.getProperty("server.servlet.context-path")));
    }

    // tag::mapping-function[]
    @Bean
    @Primary
    JmixLdapGrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        JmixLdapGrantedAuthoritiesMapper authoritiesMapper = new JmixLdapGrantedAuthoritiesMapper();
        authoritiesMapper.setDefaultRoles(ldapProperties.getDefaultRoles());
        Map<String, String> authorityMap = new HashMap<>();
        authorityMap.put("Administrators", "system-full-access");
        authoritiesMapper.setAuthorityToRoleCodeMapper(s -> authorityMap.getOrDefault(s, s));
        return authoritiesMapper;
    }
    // end::mapping-function[]

    // tag::user-repository[]
    @Bean
    @Primary
    UserRepository userRepository() {
        return new CompositeUserRepository();
    }
    // end::user-repository[]

}
