package com.company.demo.security.resserver;

import io.jmix.securityresourceserver.requestmatcher.urlprovider.AuthenticatedUrlPatternsProvider;
import org.springframework.stereotype.Component;

import java.util.List;

//tag::whole-class[]
@Component
public class GreetingAuthenticatedUrlPatternsProvider implements AuthenticatedUrlPatternsProvider {

    @Override
    public List<String> getAuthenticatedUrlPatterns() {
        return List.of("/greeting/**");
    }

}
//end::whole-class[]