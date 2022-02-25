package ldap.ex1.app;

import io.jmix.ldap.userdetails.LdapAuthorityToJmixRoleCodesMapper;
import org.springframework.stereotype.Component;

import java.util.*;

// tag::bean[]
@Component("l_CustomAuthorityMapperBean")
public class CustomAuthorityMapperBean implements LdapAuthorityToJmixRoleCodesMapper {

    @Override
    public Collection<String> mapAuthorityToJmixRoleCodes(String authority) { // <1>
        Collection<String> roleCollection = new ArrayList<>();
        if (authority.equals("mathematicians")) { // <2>
            roleCollection.add("system-full-access");
        } else {
            roleCollection.add(authority + "-resource-role"); // <3>
            roleCollection.add(authority + "-row-level-role");
        }
        return roleCollection;
    }
}
// end::bean[]
