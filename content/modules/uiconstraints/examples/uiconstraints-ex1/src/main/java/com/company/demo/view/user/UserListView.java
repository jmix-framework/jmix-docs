package com.company.demo.view.user;

import com.company.untitled82.entity.User;
import com.company.untitled82.view.main.MainView;
import com.vaadin.flow.router.Route;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {
}