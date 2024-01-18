package com.company.notificationsex1.app;

import io.jmix.notifications.channel.UserEmailResolver;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

// tag::service[]
@Component
public class GetEmailService implements UserEmailResolver {

    @Nullable
    @Override
    public String resolveEmail(UserDetails user) {
        return user.getUsername()+"company.com";
    }
}
// end::service[]