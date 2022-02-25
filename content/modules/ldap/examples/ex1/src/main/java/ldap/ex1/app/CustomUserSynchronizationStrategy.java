package ldap.ex1.app;

import io.jmix.ldap.userdetails.AbstractLdapUserDetailsSynchronizationStrategy;
import ldap.ex1.entity.User;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.stereotype.Component;

// tag::strategy[]
@Component("ldap_CustomUserSynchronizationStrategy")
public class CustomUserSynchronizationStrategy extends AbstractLdapUserDetailsSynchronizationStrategy<User> {

    private String getFirstName(String fullName) {
        return fullName.split(" ")[0];
    }

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void mapUserDetailsAttributes(User userDetails, DirContextOperations ctx) {
        userDetails.setFirstName(getFirstName(ctx.getStringAttribute("cn")));
        userDetails.setLastName(ctx.getStringAttribute("sn"));
        userDetails.setEmail(ctx.getStringAttribute("mail"));
    }
}
// end::strategy[]