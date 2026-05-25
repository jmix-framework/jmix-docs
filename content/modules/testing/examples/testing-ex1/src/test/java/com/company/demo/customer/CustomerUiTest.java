package com.company.demo.customer;

import com.company.demo.DemoApplication;
import com.company.demo.entity.Customer;
import com.company.demo.view.customer.CustomerDetailView;
import com.company.demo.view.customer.CustomerListView;
import com.vaadin.flow.component.Component;
import io.jmix.core.DataManager;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
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

import java.util.Optional;

@UiTest
@SpringBootTest(classes = {DemoApplication.class, FlowuiTestAssistConfiguration.class})
public class CustomerUiTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    ViewNavigators viewNavigators;

    @Test
    void test_createCustomer() {
        // Navigate to user list view
        viewNavigators.view(CustomerListView.class).navigate();

        CustomerListView listView = UiTestUtils.getCurrentView();

        // click "Create" button
        JmixButton createBtn = getComponent(listView, "createBtn");
        createBtn.click();

        // Get detail view
        CustomerDetailView detailView = UiTestUtils.getCurrentView();

        // Set username and password in the fields
        TypedTextField<String> nameField = getComponent(detailView, "nameField");
        String name = "test-customer-" + System.currentTimeMillis();
        nameField.setValue(name);

        // Click "OK"
        JmixButton commitAndCloseBtn = getComponent(detailView, "saveAndCloseBtn");
        commitAndCloseBtn.click();

        // Get navigated user list view
        listView = UiTestUtils.getCurrentView();

        // Check the created user is shown in the table
        DataGrid<Customer> dataGrid = getComponent(listView, "customersDataGrid");

        DataGridItems<Customer> dataGridItems = dataGrid.getItems();
        Assertions.assertNotNull(dataGridItems);

        dataGridItems.getItems().stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    @AfterEach
    void tearDown() {
        dataManager.load(Customer.class)
                .query("e.name like ?1", "test-customer-%")
                .list()
                .forEach(u -> dataManager.remove(u));
    }

    @SuppressWarnings("unchecked")
    private static <T> T getComponent(View<?> view, String componentId) {
        return (T) UiComponentUtils.getComponent(view, componentId);
    }
}
