package com.company.demo.security.resserver;

import io.jmix.securityresourceserver.requestmatcher.AnonymousRequestMatcherProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

//tag::whole-class[]
@Component
public class GreetingAnonymousRequestMatcherProvider implements AnonymousRequestMatcherProvider {

    @Override
    public RequestMatcher getAnonymousRequestMatcher() {
        return new AntPathRequestMatcher("/greeting/public/**", HttpMethod.GET.name());
    }
}
//end::whole-class[]
