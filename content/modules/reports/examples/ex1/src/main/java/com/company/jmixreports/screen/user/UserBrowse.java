package com.company.jmixreports.screen.user;

import com.company.jmixreports.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Metadata;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("jmxrpr_User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Metadata metadata;

    private void test(){
        dataManager.load(User.class).all().fetchPlan("book.edit").one();
    }
}