package com.company.demo.customer;

import com.company.demo.DemoApplication;
import com.company.demo.view.customer.CustomerDetailView;
import com.company.demo.view.customer.CustomerListView;
import com.vaadin.flow.component.Component;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@UiTest
@SpringBootTest(classes = {DemoApplication.class, FlowuiTestAssistConfiguration.class})
public class CustomerDialogUiTest {

    // tag::viewNavigators[]
    @Autowired
    private ViewNavigators viewNavigators;

    // end::viewNavigators[]

    // tag::dialogWindows[]
    @Autowired
    private DialogWindows dialogWindows;

    // end::dialogWindows[]

    // tag::test-1[]
    @Test
    void test_openCustomerDetailViewInDialog() {
        viewNavigators.view(UiTestUtils.getCurrentView(), CustomerListView.class)
                .navigate();

        CustomerListView listView = UiTestUtils.getCurrentView();
        JmixButton createDialogBtn = getComponent(listView, "createDialogBtn");
        createDialogBtn.click();

        CustomerDetailView detailView = dialogWindows.getOpenedDialogWindows()
                .getDialogs()
                .stream()
                .filter(CustomerDetailView.class::isInstance)
                .map(CustomerDetailView.class::cast)
                .reduce((previous, current) -> current)
                .orElseThrow();

        Assertions.assertNotNull(detailView);
    }
    // end::test-1[]

    @SuppressWarnings("unchecked")
    private static <T> T getComponent(View<?> view, String componentId) {
        Optional<Component> component = UiComponentUtils.findComponent(view, componentId);
        Assertions.assertTrue(component.isPresent());
        return (T) component.get();
    }
}