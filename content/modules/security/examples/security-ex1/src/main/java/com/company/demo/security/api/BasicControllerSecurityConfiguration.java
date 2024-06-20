
package com.company.demo.security.api;

import io.jmix.core.JmixSecurityFilterChainOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Profile("endpoints-security")
//tag::whole-class[]
@Configuration
public class BasicControllerSecurityConfiguration {

    @Bean
    @Order(JmixSecurityFilterChainOrder.CUSTOM) //<1>
    SecurityFilterChain basicControllerFilterChain(
            HttpSecurity http,
            AuthenticationManager authenticationManager) throws Exception {
        http.securityMatcher("/api/**") //<2>
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/public/**").permitAll() //<3>
                                .anyRequest().authenticated() //<4>
                )
                .httpBasic(Customizer.withDefaults()) //<5>
                .authenticationManager(authenticationManager); //<6>
        return http.build();
    }
}
//end::whole-class[]