package ldap.ex1.service;

import io.jmix.ldap.userdetails.AbstractLdapUserDetailsSynchronizationStrategy;
import ldap.ex1.entity.User;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.stereotype.Component;

// tag::strategy[]
@Component("ldap_MyUserSynchronizationStrategy")
public class MyUserSynchronizationStrategy extends AbstractLdapUserDetailsSynchronizationStrategy<User> {

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void mapUserDetailsAttributes(User userDetails, DirContextOperations ctx) {
        userDetails.setFirstName(ctx.getStringAttribute("givenName"));
        userDetails.setLastName(ctx.getStringAttribute("sn"));
    }

}
// end::strategy[]