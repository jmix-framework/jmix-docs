package bpm.ex1.service;

import bpm.ex1.entity.User;
import io.jmix.bpm.provider.UserProvider;
import io.jmix.core.DataManager;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// tag::user-provider[]
@Component("smpl_MyUserProvider")
public class MyUserProvider implements UserProvider {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public UserDetails getValue(String executionId) {
        String manager = (String) runtimeService.getVariable(executionId, "manager");
        return dataManager.load(User.class)
                .query("select u from smpl_User u where u.username = :username")
                .parameter("username", manager)
                .one();
    }
}
// end::user-provider[]