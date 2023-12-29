package examples.oidcex1.config;

import io.jmix.core.security.AuthorizedUrlsProvider;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collection;

public class ProtectedApiConfiguration {

    //tag::my-authorized-urls-provider[]
    @Bean
    public AuthorizedUrlsProvider myAuthorizedUrlsProvider() {
        return new AuthorizedUrlsProvider() {
            @Override
            public Collection<String> getAuthenticatedUrlPatterns() {
                return Arrays.asList("/authenticated/**");
            }

            @Override
            public Collection<String> getAnonymousUrlPatterns() {
                return Arrays.asList("/anonymous/**");
            }
        };
    }
    //end::my-authorized-urls-provider[]
}
