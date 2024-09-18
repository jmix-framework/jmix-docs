package com.company.demo.security.resserver;

import io.jmix.securityresourceserver.requestmatcher.AuthenticatedRequestMatcherProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

//tag::whole-class[]
@Component
public class GreetingAuthenticatedRequestMatcherProvider implements AuthenticatedRequestMatcherProvider {

    @Override
    public RequestMatcher getAuthenticatedRequestMatcher() {
        return new AntPathRequestMatcher("/greeting/**", HttpMethod.POST.name());
    }
}
//end::whole-class[]