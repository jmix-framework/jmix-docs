package com.company.bpmex1.provider;

import com.company.bpmex1.entity.User;
import io.jmix.bpm.provider.UserProvider;
import io.jmix.core.UnconstrainedDataManager;
import org.springframework.beans.factory.annotation.Autowired;

// tag::user-provider[]
@UserProvider(value = "UserByEmailProvider", description = "Returns the user with the given email")
public class UserByEmailProvider {

    @Autowired
    private UnconstrainedDataManager dataManager; // <1>

    public String getUserByEmail(String email) {
        return dataManager.load(User.class)
                .query("select u from User u where u.email = :email")
                .parameter("email", email)
                .one()
                .getUsername();
    }
}
// end::user-provider[]
