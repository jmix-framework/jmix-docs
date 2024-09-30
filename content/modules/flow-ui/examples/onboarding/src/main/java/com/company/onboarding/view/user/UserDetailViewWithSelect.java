package com.company.onboarding.view.user;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "usersselect/:id", layout = MainView.class)
@ViewController("User.detailwithselect")
@ViewDescriptor("user-detail-view-with-select.xml")
@EditedEntityContainer("userDc")
public class UserDetailViewWithSelect extends StandardDetailView<User> {
}