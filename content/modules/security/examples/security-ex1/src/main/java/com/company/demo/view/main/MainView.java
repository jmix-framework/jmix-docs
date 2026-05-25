package com.company.demo.view.main;

import com.company.demo.entity.User;
import com.vaadin.flow.router.Route;
import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    // tag::user-substitution[]
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    private String getSubstitutedUserName() {
        User substitutedUser = (User) currentUserSubstitution.getSubstitutedUser();
        return substitutedUser == null ? "" : substitutedUser.getUsername();
    }
    // end::user-substitution[]
}
