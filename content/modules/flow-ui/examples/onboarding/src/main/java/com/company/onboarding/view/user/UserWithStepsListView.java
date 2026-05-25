package com.company.onboarding.view.user;

import com.company.onboarding.entity.User;

import com.company.onboarding.entity.UserStep;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;

// tag::depend[]
@Route(value = "users-with-steps", layout = MainView.class)
@ViewController("UserWithStepsListView")
@ViewDescriptor("user-with-steps-list-view.xml")
@LookupComponent("usersTable")
@DialogMode(width = "50em", height = "37.5em")
public class UserWithStepsListView extends StandardListView<User> {

    @ViewComponent
    private CollectionLoader<User> usersDl;
    @ViewComponent
    private CollectionLoader<UserStep> userStepsDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        usersDl.load(); // <1>
    }

    @Subscribe(id = "usersDc", target = Target.DATA_CONTAINER)
    public void onUsersDcItemChange(final InstanceContainer.ItemChangeEvent<User> event) {
        userStepsDl.setParameter("user", event.getItem()); // <2>
        userStepsDl.load();
    }
}
// end::depend[]
