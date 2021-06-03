package security.ex1.screen.auth;

import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import security.ex1.entity.Customer;

import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

@UiController("sample_AuthTest")
@UiDescriptor("auth-test.xml")
public class AuthTestScreen extends Screen {

    @Autowired
    private SystemAuthenticator systemAuthenticator;

    @Subscribe("showCurrentAuth")
    public void onShowCurrentAuthClick(Button.ClickEvent event) {
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

    @Subscribe("testSystemAuth")
    public void onTestSystemAuthClick(Button.ClickEvent event) {
        systemAuthenticator.withSystem(() -> {
            Authentication authentication = currentAuthentication.getAuthentication();
            UserDetails user = currentAuthentication.getUser();
            Locale locale = currentAuthentication.getLocale();
            TimeZone timeZone = currentAuthentication.getTimeZone();

            System.out.println(
                    ">>> System\n" +
                    "User: " + user.getUsername() + "\n" +
                    "Authentication: " + authentication + "\n" +
                    "Roles: " + getRoleNames(authentication) + "\n" +
                    "Locale: " + locale.getDisplayName() + "\n" +
                    "TimeZone: " + timeZone.getDisplayName()
            );
            return null;
        });
    }

}