package com.company.demo.security;

import io.jmix.core.security.event.UserSubstitutedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSubstitutionEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(UserSubstitutionEventListener.class);

    // tag::user-substitution-event[]
    @EventListener
    public void onUserSubstituted(UserSubstitutedEvent event) {
        UserDetails authenticatedUser = event.getAuthenticatedUser();
        UserDetails substitutedUser = event.getSubstitutedUser();
        log.info("User substituted: " + authenticatedUser.getUsername()
                + " -> " + substitutedUser.getUsername());
    }
    // end::user-substitution-event[]
}
