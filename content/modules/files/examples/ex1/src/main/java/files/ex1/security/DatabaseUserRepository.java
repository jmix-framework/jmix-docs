package files.ex1.security;

import files.ex1.entity.User;
import io.jmix.core.Metadata;
import io.jmix.securitydata.user.AbstractDatabaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Primary
@Component("UserRepository")
public class DatabaseUserRepository extends AbstractDatabaseUserRepository<User> {

    @Autowired
    private Metadata metadata;

    @Override
    protected Class<User> getUserClass() {
        return User.class;
    }

    @Override
    protected void initSystemUser(User systemUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccessRole.CODE)
                .build();
        systemUser.setAuthorities(authorities);
    }

    @Override
    protected void initAnonymousUser(User anonymousUser) {
        Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                .addResourceRole(FullAccessRole.CODE)
                .build();
        anonymousUser.setAuthorities(authorities);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            User user = metadata.create(User.class);
            user.setUsername("admn");
            user.setPassword("{noop}admin");
            user.setActive(true);
            Collection<GrantedAuthority> authorities = getGrantedAuthoritiesBuilder()
                    .addResourceRole(FullAccessRole.CODE)
                    .build();
            user.setAuthorities(authorities);
            return user;
        }
        return super.loadUserByUsername(username);
    }
}