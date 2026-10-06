package com.company.sample.drafts;

import com.company.sample.SampleApplication;
import com.company.sample.entity.Customer;
import com.company.sample.entity.Order;
import com.company.sample.job.DraftsCleanupJob;
import com.company.sample.view.customer.CustomerDetailView;
import com.company.sample.view.customer.CustomerListView;
import com.company.sample.view.main.MainView;
import com.company.sample.view.order.OrderDetailView;
import com.company.sample.view.order.OrderListView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.RouteParameters;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.testassist.dialog.DialogInfo;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewControllerUtils;
import io.jmix.flowui.view.navigation.ViewNavigationSupport;
import io.jmix.uirecovery.entity.ViewDraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UiTest
@SpringBootTest(classes = {SampleApplication.class, FlowuiTestAssistConfiguration.class})
@ActiveProfiles("test")
public class DraftsUiTest {

    @Autowired
    UnconstrainedDataManager dataManager;

    @Autowired
    ViewNavigationSupport navigationSupport;

    @Autowired
    DraftsCleanupJob draftsCleanupJob;

    Order order;
    Customer customer;

    @BeforeEach
    void setUp() {
        order = dataManager.create(Order.class);
        order.setNumber("initial");
        order = dataManager.save(order);

        customer = dataManager.create(Customer.class);
        customer.setName("initial");
        customer = dataManager.save(customer);
    }

    @AfterEach
    void tearDown() {
        dataManager.load(ViewDraft.class).all().list().forEach(dataManager::remove);
        dataManager.load(Order.class).all().list().forEach(dataManager::remove);
        dataManager.load(Customer.class).all().list().forEach(dataManager::remove);
    }

    @Test
    void test_changeWritesDraft() {
        OrderDetailView view = openView(OrderDetailView.class, order.getId());
        view.getEditedEntity().setNumber("drafted");

        List<ViewDraft> drafts = drafts();
        assertEquals(1, drafts.size());
        assertEquals("Order_.detail", drafts.get(0).getViewId());
    }

    @Test
    void test_saveDeletesDraft() {
        OrderDetailView view = openView(OrderDetailView.class, order.getId());
        view.getEditedEntity().setNumber("drafted");
        assertEquals(1, drafts().size());

        view.save();

        assertTrue(drafts().isEmpty());
    }

    @Test
    void test_askModeRestoresAfterConfirmation() {
        createDraft(OrderDetailView.class, order.getId(), "drafted");

        OrderDetailView view = openView(OrderDetailView.class, order.getId());
        assertEquals("initial", view.getEditedEntity().getNumber());

        DialogInfo dialog = UiTestUtils.getLastOpenedDialog();
        assertNotNull(dialog);
        Button restoreButton = dialog.getButtons().get(0);
        assertEquals("Restore", restoreButton.getText());
        restoreButton.click();

        assertEquals("drafted", view.getEditedEntity().getNumber());
    }

    @Test
    void test_restoreDelegateDeletesOldDraft() {
        createDraft(OrderDetailView.class, order.getId(), "drafted");
        ViewDraft draft = drafts().get(0);
        draft.setCreatedDate(OffsetDateTime.now().minusDays(2));
        dataManager.save(draft);
        int dialogCount = UiTestUtils.getOpenedDialogs().size();

        OrderDetailView view = openView(OrderDetailView.class, order.getId());

        assertEquals(dialogCount, UiTestUtils.getOpenedDialogs().size());
        assertEquals("initial", view.getEditedEntity().getNumber());
        assertTrue(drafts().isEmpty());
    }

    @Test
    void test_autoModeRestoresWithoutDialog() {
        createDraft(CustomerDetailView.class, customer.getId(), "drafted");

        CustomerDetailView view = openView(CustomerDetailView.class, customer.getId());

        assertEquals("drafted", view.getEditedEntity().getName());
        assertTrue(ViewControllerUtils.getViewData(view).getDataContext().hasChanges());
    }

    @Test
    void test_cleanupJobDeletesExpiredDrafts() {
        createDraft(OrderDetailView.class, order.getId(), "drafted");
        ViewDraft draft = drafts().get(0);
        draft.setCreatedDate(OffsetDateTime.now().minusDays(31));
        dataManager.save(draft);

        draftsCleanupJob.deleteExpiredDrafts();

        assertTrue(drafts().isEmpty());
    }

    @Test
    void test_listViewsOpen() {
        navigationSupport.navigate(CustomerListView.class);
        assertInstanceOf(CustomerListView.class, UiTestUtils.getCurrentView());

        navigationSupport.navigate(OrderListView.class);
        assertInstanceOf(OrderListView.class, UiTestUtils.getCurrentView());
    }

    private <V extends StandardDetailView<?>> V openView(Class<V> viewClass, UUID id) {
        // Open another view first, so that a new view instance is created each time
        navigationSupport.navigate(MainView.class);
        navigationSupport.navigate(viewClass, new RouteParameters("id", id.toString()));
        return UiTestUtils.getCurrentView();
    }

    private void createDraft(Class<? extends StandardDetailView<?>> viewClass, UUID id, String value) {
        StandardDetailView<?> view = openView(viewClass, id);
        Object entity = view.getEditedEntity();
        if (entity instanceof Order o) {
            o.setNumber(value);
        } else if (entity instanceof Customer c) {
            c.setName(value);
        }
        assertEquals(1, drafts().size());
        // Leave the view without saving or discarding, as if the session was lost
        view.clearChanges();
    }

    private List<ViewDraft> drafts() {
        return dataManager.load(ViewDraft.class).all().list();
    }
}
