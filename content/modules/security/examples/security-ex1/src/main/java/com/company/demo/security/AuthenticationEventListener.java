package com.company.demo.security;

import com.company.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

// tag::class[]
@Component
public class AuthenticationEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(AuthenticationEventListener.class);

    @EventListener
    public void onInteractiveAuthenticationSuccess(
            InteractiveAuthenticationSuccessEvent event) { // <1>
        User user = (User) event.getAuthentication().getPrincipal(); // <2>
        log.info("User logged in: " + user.getUsername());
    }

    @EventListener
    public void onAuthenticationSuccess(
            AuthenticationSuccessEvent event) { // <3>
        User user = (User) event.getAuthentication().getPrincipal(); // <4>
        log.info("User authenticated " + user.getUsername());
    }

    @EventListener
    public void onAuthenticationFailure(
            AbstractAuthenticationFailureEvent event) { // <5>
        String username = (String) event.getAuthentication().getPrincipal(); // <6>
        log.info("User login attempt failed: " + username);
    }

    @EventListener
    public void onLogoutSuccess(LogoutSuccessEvent event) { // <7>
        User user = (User) event.getAuthentication().getPrincipal(); // <8>
        log.info("User logged out: " + user.getUsername());
    }
}
// end::class[]
