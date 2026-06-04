package com.company.demo.security.resserver;

import io.jmix.securityresourceserver.requestmatcher.AuthenticatedRequestMatcherProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

//tag::whole-class[]
@Component
public class GreetingAuthenticatedRequestMatcherProvider implements AuthenticatedRequestMatcherProvider {

    @Override
    public RequestMatcher getAuthenticatedRequestMatcher() {
        return PathPatternRequestMatcher.pathPattern(HttpMethod.POST, "/api/**");
    }
}
//end::whole-class[]