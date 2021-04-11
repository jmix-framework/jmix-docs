package security.ex1.mbean;

import io.jmix.core.security.Authenticated;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.core.security.SystemAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(description = "JMX bean for some settings", objectName = "com.company.app:type=SettingsBean")
public class SettingsManagementFacade {

    // tag::system-authenticator[]
    @Autowired
    private SystemAuthenticator systemAuthenticator;
    // end::system-authenticator[]

    // tag::current-authentication[]
    @Autowired
    private CurrentAuthentication currentAuthentication;
    // end::current-authentication[]

    // tag::system-auth-code[]

    @ManagedOperation
    public String doSomething() {
        return systemAuthenticator.withSystem(() -> {
            UserDetails user = currentAuthentication.getUser();
            System.out.println("User: " + user.getUsername()); // system
            // ...
            return "Done";
        });
    }

    @ManagedOperation
    public String doSomething2() {
        return systemAuthenticator.withUser("admin", () -> {
            UserDetails user = currentAuthentication.getUser();
            System.out.println("User: " + user.getUsername()); // admin
            // ...
            return "Done";
        });
    }
    // end::system-auth-code[]

    // tag::system-auth-ann[]

    @Authenticated // authenticates the entire method
    @ManagedOperation
    public String doSomething3() {
        UserDetails user = currentAuthentication.getUser();
        System.out.println("User: " + user.getUsername()); // system
        // ...
        return "Done";
    }
    // end::system-auth-ann[]

}