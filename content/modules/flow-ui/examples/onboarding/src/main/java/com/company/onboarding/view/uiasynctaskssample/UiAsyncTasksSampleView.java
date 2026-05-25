package com.company.onboarding.view.uiasynctaskssample;


import com.company.onboarding.entity.Customer;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.asynctask.UiAsyncTasks;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Route(value = "ui-async-tasks-sample-view", layout = MainView.class)
@ViewController("UiAsyncTasksSampleView")
@ViewDescriptor("ui-async-tasks-sample-view.xml")
public class UiAsyncTasksSampleView extends StandardView {


    @Autowired
    private CustomerService customerService;
    @ViewComponent
    private CollectionContainer<Customer> customersDc;
    @ViewComponent
    private TypedTextField<Object> errorField;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private TypedTextField<Object> resultField;

    // tag::supply-async[]
    @Autowired
    private UiAsyncTasks uiAsyncTasks;

    private void loadCustomersAsync() {
        uiAsyncTasks.supplierConfigurer(this::loadCustomers) // <1>
                .withResultHandler(customers -> {
                    customersDc.setItems(customers); // <2>
                    notifications.create("Customers loaded").show();
                })
                .supplyAsync();
    }
    // end::supply-async[]

    // tag::run-async[]
    private void synchronizeCustomersAsync() {
        uiAsyncTasks.runnableConfigurer(this::synchronizeCustomers)
                .withResultHandler(() -> {
                    resultField.setValue("Synchronization completed");
                })
                .runAsync();
    }
    //end::run-async[]

    // tag::timeout[]
    private void loadCustomersWithTimeout() {
        uiAsyncTasks.supplierConfigurer(this::loadCustomers)
                .withResultHandler(customers -> {
                    //...
                })
                .withTimeout(20, TimeUnit.SECONDS)
                .withExceptionHandler(ex -> {
                    String errorText;
                    if (ex instanceof TimeoutException) {
                        errorText = "Timeout exceeded";
                    } else {
                        errorText = ex.getMessage();
                    }
                    errorField.setValue(errorText);
                })
                .supplyAsync();
    }
    // end::timeout[]

    // tag::exception-handler[]
    private void loadCustomersAndHandleException() {
        uiAsyncTasks.supplierConfigurer(this::loadCustomers)
                .withResultHandler(customers -> {
                    //...
                })
                .withExceptionHandler(ex -> {
                    errorField.setValue(ex.getMessage());
                })
                .supplyAsync();
    }
    // end::exception-handler[]

    // tag::load-customers[]

    private List<Customer> loadCustomers() {
        return customerService.loadCustomers();
    }
    // end::load-customers[]

    // tag::synchronize-customers[]

    private void synchronizeCustomers() {
        customerService.synchronizeCustomers();
    }
    // end::synchronize-customers[]

    @Subscribe(id = "loadCustomersAsyncBtn", subject = "clickListener")
    public void onLoadCustomersAsyncBtnClick(final ClickEvent<JmixButton> event) {
        loadCustomersAsync();
    }
}