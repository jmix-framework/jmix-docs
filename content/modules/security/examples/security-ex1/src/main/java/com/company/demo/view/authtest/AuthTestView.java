package com.company.demo.view.authtest;


import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Route(value = "AuthTestView", layout = MainView.class)
@ViewController("AuthTestView")
@ViewDescriptor("auth-test-view.xml")
public class AuthTestView extends StandardView {

    @Subscribe("showCurrentAuthBtn")
    public void onShowCurrentAuthBtnClick(final ClickEvent<Button> event) {
        printAuthenticationInfo();
    }

    // tag::current-auth[]
    @Autowired
    private CurrentAuthentication currentAuthentication;

    private void printAuthenticationInfo() {
        UserDetails user = currentAuthentication.getUser();
        Authentication authentication = currentAuthentication.getAuthentication();
        Locale locale = currentAuthentication.getLocale();
        TimeZone timeZone = currentAuthentication.getTimeZone();

        System.out.println(
                "User: " + user.getUsername() + "\n" +
                        "Authentication: " + authentication + "\n" +
                        "Roles: " + getRoleNames(authentication) + "\n" +
                        "Locale: " + locale.getDisplayName() + "\n" +
                        "TimeZone: " + timeZone.getDisplayName()
        );
    }

    private String getRoleNames(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
    // end::current-auth[]
}