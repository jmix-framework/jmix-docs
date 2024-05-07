package com.company.demo.security.api;

import io.jmix.core.JmixSecurityFilterChainOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("endpoints-security")
//tag::whole-class[]
@Configuration
public class AnonymousControllerSecurityConfiguration {

    @Bean
    @Order(JmixSecurityFilterChainOrder.CUSTOM) //<1>
    SecurityFilterChain greetingFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/greeting/**") //<2>
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll() //<3>
                )
                .csrf(csrf -> csrf.disable()); //<4>
        return http.build();
    }
}
//end::whole-class[]