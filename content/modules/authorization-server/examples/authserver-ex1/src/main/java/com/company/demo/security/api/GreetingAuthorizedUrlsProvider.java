package com.company.demo.security.api;

import io.jmix.core.security.AuthorizedUrlsProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Profile("endpoints-security")
//tag::whole-class[]
@Component
public class GreetingAuthorizedUrlsProvider implements AuthorizedUrlsProvider {

    @Override
    public Collection<String> getAuthenticatedUrlPatterns() {
        return List.of("/greeting/**");
    }

    @Override
    public Collection<String> getAnonymousUrlPatterns() {
        return List.of();
    }
}
//end::whole-class[]