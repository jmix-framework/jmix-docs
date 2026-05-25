package com.company.demo.user;

import com.company.demo.DemoApplication;
import com.company.demo.entity.User;
import com.company.demo.view.user.UserDetailView;
import com.company.demo.view.user.UserListView;
import io.jmix.core.DataManager;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.data.grid.DataGridItems;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Sample UI integration test for the User entity.
 */
@UiTest // <1>
@SpringBootTest(classes = {DemoApplication.class, FlowuiTestAssistConfiguration.class}) // <2>
public class UserUiTest {

    @Autowired
    DataManager dataManager; // <3>

    @Autowired
    ViewNavigators viewNavigators;

    @Test // <4>
    void test_createUser() {
        // Navigate to user list view
        viewNavigators.view(UiTestUtils.getCurrentView(), UserListView.class).navigate();

        UserListView userListView = UiTestUtils.getCurrentView(); // <5>

        // click "Create" button
        JmixButton createBtn = findComponent(userListView, "createBtn"); // <6>
        createBtn.click();

        // Get detail view
        UserDetailView userDetailView = UiTestUtils.getCurrentView();

        // Set username and password in the fields
        TypedTextField<String> usernameField = findComponent(userDetailView, "usernameField");
        String username = "test-user-" + System.currentTimeMillis();
        usernameField.setValue(username);

        JmixPasswordField passwordField = findComponent(userDetailView, "passwordField");
        passwordField.setValue("test-passwd");

        JmixPasswordField confirmPasswordField = findComponent(userDetailView, "confirmPasswordField");
        confirmPasswordField.setValue("test-passwd");

        // Click "OK"
        JmixButton commitAndCloseBtn = findComponent(userDetailView, "saveAndCloseBtn");
        commitAndCloseBtn.click();

        // Get navigated user list view
        userListView = UiTestUtils.getCurrentView();

        // Check the created user is shown in the table
        DataGrid<User> usersDataGrid = findComponent(userListView, "usersDataGrid");

        DataGridItems<User> usersDataGridItems = usersDataGrid.getItems();
        Assertions.assertNotNull(usersDataGridItems);

        usersDataGridItems.getItems().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow();
    }

    @AfterEach // <7>
    void tearDown() {
        dataManager.load(User.class)
                .query("e.username like ?1", "test-user-%")
                .list()
                .forEach(u -> dataManager.remove(u));
    }

    @SuppressWarnings("unchecked")
    private static <T> T findComponent(View<?> view, String componentId) {
        return (T) UiComponentUtils.getComponent(view, componentId);
    }
}
