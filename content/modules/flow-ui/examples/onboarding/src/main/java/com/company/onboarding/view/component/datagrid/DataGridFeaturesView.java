package com.company.onboarding.view.component.datagrid;


import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
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
    @ViewComponent
    private CollectionContainer<User> usersDc;
    @ViewComponent
    private CollectionLoader<User> usersDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UiComponents uiComponents;

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


    // tag::auto-save[]
    @Subscribe(id = "usersDc", target = Target.DATA_CONTAINER)
    public void onUsersDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<User> event) {
        dataManager.save(event.getItem());
    }

    // end::auto-save[]
    // tag::renderer[]
    @Supply(to = "dataGridCheckbox.active", subject = "renderer")
    private Renderer<User> dataGridCheckboxActiveRenderer() {
        return new ComponentRenderer<>(
                () -> {
                    JmixCheckbox checkbox = uiComponents.create(JmixCheckbox.class); // <1>
                    checkbox.setReadOnly(true); // <2>
                    return checkbox; // <3>
                },
                (checkbox, user) -> checkbox.setValue(user.getActive()) // <4>
        );
    }
    // end::renderer[]
    // tag::lit-renderer[]
    @Supply(to = "dataGridLit.userInfo", subject = "renderer")
    private Renderer<User> dataGridLitUserInfoRenderer() {
        return LitRenderer.<User>of("${item.firstName}<br>${item.lastName}<br>${item.email}")
                .withProperty("firstName", User::getFirstName)
                .withProperty("lastName", User::getLastName)
                .withProperty("email", User::getEmail);
    }
    // end::lit-renderer[]
}