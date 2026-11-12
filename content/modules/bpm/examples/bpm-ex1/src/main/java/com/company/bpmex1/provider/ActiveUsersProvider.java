package com.company.bpmex1.provider;

import com.company.bpmex1.entity.User;
import io.jmix.bpm.provider.UserListProvider;
import io.jmix.core.UnconstrainedDataManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// tag::user-list-provider[]
@UserListProvider(value = "ActiveUsersProvider", description = "Returns all active users")
public class ActiveUsersProvider {

    @Autowired
    private UnconstrainedDataManager dataManager;

    public List<String> getActiveUsers() {
        return dataManager.load(User.class)
                .query("select u from User u where u.active = true")
                .list()
                .stream()
                .map(User::getUsername)
                .toList();
    }
}
// end::user-list-provider[]
