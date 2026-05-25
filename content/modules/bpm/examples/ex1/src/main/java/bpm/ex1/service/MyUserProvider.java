package bpm.ex1.service;

import bpm.ex1.entity.User;
import io.jmix.bpm.provider.UserProvider;
import io.jmix.core.DataManager;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// tag::user-provider[]
@UserProvider(value = "smpl_MyUserProvider", description = "Returns a user with the specified email")
public class MyUserProvider {

    @Autowired
    private DataManager dataManager;

    public String getUserByEmail(String parameter) {
        return dataManager.load(User.class)
                .query("select u from smpl_User u where u.email = :email")
                .parameter("email", parameter)
                .one()
                .getUsername();
    }
}
// end::user-provider[]