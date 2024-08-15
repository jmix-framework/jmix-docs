package com.company.demo.security.resserver;

import io.jmix.securityresourceserver.requestmatcher.urlprovider.AnonymousUrlPatternsProvider;
import org.springframework.stereotype.Component;

import java.util.List;

//tag::whole-class[]
@Component
public class GreetingAnonymousUrlPatternsProvider implements AnonymousUrlPatternsProvider {

    @Override
    public List<String> getAnonymousUrlPatterns() {
        return List.of("/greeting/public/**");
    }

}
//end::whole-class[]
