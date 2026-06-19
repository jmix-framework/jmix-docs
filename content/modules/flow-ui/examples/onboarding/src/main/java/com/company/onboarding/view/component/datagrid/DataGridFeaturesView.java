package com.company.onboarding.view.component.datagrid;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.grid.editor.EditorCloseEvent;
import com.vaadin.flow.component.grid.editor.EditorSaveEvent;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Route(value = "data-grid-features-view", layout = MainView.class)
@ViewController("DataGridFeaturesView")
@ViewDescriptor("data-grid-features-view.xml")
public class DataGridFeaturesView extends StandardView {

    // tag::injects[]
    @ViewComponent
    private DataGrid<User> dataGrid;
    // tag::auto-save-non-buffered[]
    @ViewComponent
    private CollectionContainer<User> usersDc;
    // end::auto-save-non-buffered[]
    @ViewComponent
    private CollectionLoader<User> usersDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private DataContext dataContext;

    // end::injects[]

    // tag::onInit[]
    @Subscribe
    protected void onInit(final InitEvent event) {
        usersDl.load();
        initHeader();
        initFooter();
    }

    // end::onInit[]

    // tag::initHeader[]
    protected void initHeader() {
        HeaderRow headerRow = dataGrid.prependHeaderRow();
        HeaderRow.HeaderCell headerCell = headerRow.join(
                dataGrid.getColumnByKey("firstName"),
                dataGrid.getColumnByKey("lastName")); // <--1-->
        headerCell.setText("Full Name");
    }

    // end::initHeader[]

    // tag::initFooter[]
    protected void initFooter() {
        FooterRow footerRow = dataGrid.appendFooterRow();
        FooterRow.FooterCell activeCell = footerRow.getCell(dataGrid.getColumnByKey("active"));
        activeCell.setText(getActiveCount() + "/" + usersDc.getItems().size());
    }

    // end::initFooter[]

    // tag::getActiveCount[]
    protected int getActiveCount() {
        int activeCount = 0;
        Collection<User> items = dataGrid.getGenericDataView().getItems().toList();
        for (User user : items) {
            if (user.getActive()) {
                activeCount++;
            }
        }
        return activeCount;
    }

    // end::getActiveCount[]

    // tag::auto-save-non-buffered[]

    @Install(to = "usersDataGrid.@editor", subject = "closeListener")
    private void usersDataGridEditorCloseListener(final EditorCloseEvent<User> event) {
        User user = event.getItem();
        User savedUser = dataManager.save(user); // <1>
        usersDc.replaceItem(savedUser); // <2>
    }
    // end::auto-save-non-buffered[]

    // tag::auto-save-buffered[]
    @Install(to = "usersDataGrid.@editor", subject = "saveListener")
    private void usersDataGridEditorSaveListener(final EditorSaveEvent<User> event) {
        User user = event.getItem();
        User savedUser = dataManager.save(user);
        usersDc.replaceItem(savedUser);
    }
    // end::auto-save-buffered[]

    // tag::renderer[]
    @Supply(to = "dataGridCheckbox.active", subject = "renderer") // <1>
    private Renderer<User> dataGridCheckboxActiveRenderer() {
        return new ComponentRenderer<>(
                () -> {
                    JmixCheckbox checkbox = uiComponents.create(JmixCheckbox.class); // <2>
                    checkbox.setReadOnly(true); // <3>
                    return checkbox; // <4>
                },
                (checkbox, item) -> checkbox.setValue(item.getActive()) // <5>
        );
    }
    // end::renderer[]

    // tag::renderer-update[]
    @Supply(to = "dataGridStatus.status", subject = "renderer")
    private Renderer<User> createUpdatableStatusRenderer() {
        return new ComponentRenderer<>(
                item -> {
                    Span span = uiComponents.create(Span.class); // <1>
                    updateSpanBadge(span, item); // <2>
                    return span; // <3>
                },
                (component, item) -> {
                    if (item.getOnboardingStatus() == null) { // <4>
                        updateUnknownStatus(component); // <5>
                        return component; // <6>
                    } else {
                        return createStatusBadge(item); // <7>
                    }
                }
        );
    }
    // end::renderer-update[]

    private void updateSpanBadge(Span span, User item) {
        span.getElement().getThemeList().clear();
        span.getElement().getThemeList().add("badge");

        if (item.getOnboardingStatus() == null) {
            span.setText("Unknown");
            span.getElement().getThemeList().add("contrast");
        } else {
            span.setText(item.getOnboardingStatus().name().replace('_', ' '));

            switch (item.getOnboardingStatus()) {
                case NOT_STARTED -> span.getElement().getThemeList().add("contrast");
                case IN_PROGRESS -> span.getElement().getThemeList().add("warning");
                case COMPLETED -> span.getElement().getThemeList().add("success");
            }
        }
    }

    private void updateUnknownStatus(com.vaadin.flow.component.Component component) {
        ((HasText) component).setText("Unknown");
        component.getElement().getThemeList().clear();
        component.getElement().getThemeList().add("badge contrast");
    }

    private Div createStatusBadge(User item) {
        Div div = new Div(item.getOnboardingStatus().name().replace('_', ' '));
        div.getElement().getThemeList().add("badge");

        switch (item.getOnboardingStatus()) {
            case NOT_STARTED -> div.getElement().getThemeList().add("contrast");
            case IN_PROGRESS -> div.getElement().getThemeList().add("warning");
            case COMPLETED -> div.getElement().getThemeList().add("success");
        }

        return div;
    }
    // tag::lit-renderer[]
    @Supply(to = "dataGridLit.userInfo", subject = "renderer")
    private Renderer<User> dataGridLitUserInfoRenderer() {
        return LitRenderer.<User>of("${item.firstName}<br>${item.lastName}<br>${item.email}")
                .withProperty("firstName", User::getFirstName)
                .withProperty("lastName", User::getLastName)
                .withProperty("email", User::getEmail);
    }
    // end::lit-renderer[]
    @Subscribe(id = "newCustomerBtn", subject = "clickListener")
    public void onNewCustomerBtnClick(final ClickEvent<JmixButton> event) {
        notifications.show("This is singleClickListener");
    }
}
