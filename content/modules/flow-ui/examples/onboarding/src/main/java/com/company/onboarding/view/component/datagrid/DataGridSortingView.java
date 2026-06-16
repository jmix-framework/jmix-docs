package com.company.onboarding.view.component.datagrid;

import com.company.onboarding.entity.Department;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.sort.DataGridSortBuilder;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Supply;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;

@Route(value = "data-grid-sorting-view", layout = MainView.class)
@ViewController("DataGridSortingView")
@ViewDescriptor("data-grid-sorting-view.xml")
public class DataGridSortingView extends StandardView {

    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private CollectionContainer<Department> inMemoryDepartmentsDc;

    @ViewComponent
    private DataGrid<Department> inMemoryDepartmentsDataGrid;

    @ViewComponent
    private DataGrid<Department> delegateDepartmentsDataGrid;

    @ViewComponent
    private DataGrid<User> fullNameUsersDataGrid;

    @Subscribe
    public void onInit(final InitEvent event) {
        initInMemoryComparator();
        initBoundPropertySortBuilder();
        initComputedColumnSortBuilder();
    }

    // tag::in-memory-comparator[]
    private void initInMemoryComparator() {
        inMemoryDepartmentsDc.setItems(dataManager.load(Department.class)
                .query("select e from Department e")
                .list());

        inMemoryDepartmentsDataGrid.getColumnByKey("num")
                .setComparator(Comparator.comparing(
                        department -> department.getNum() == null ? null : Integer.valueOf(department.getNum()),
                        Comparator.nullsFirst(Integer::compareTo)));
    }
    // end::in-memory-comparator[]

    // tag::sort-builder-bound-property[]
    private void initBoundPropertySortBuilder() {
        delegateDepartmentsDataGrid.setSortBuilderDelegate(sortContext ->
                DataGridSortBuilder.create(sortContext)
                        .replaceSort("num", "CAST({E}.num BIGINT)", // <1>
                                Comparator.comparing(
                                        department -> department.getNum() == null
                                                ? null
                                                : Integer.valueOf(department.getNum()),
                                        Comparator.nullsFirst(Integer::compareTo))) // <2>
                        .build());
    }
    // end::sort-builder-bound-property[]

    // tag::sort-builder-computed-column[]
    private void initComputedColumnSortBuilder() {
        fullNameUsersDataGrid.setSortBuilderDelegate(sortContext ->
                DataGridSortBuilder.create(sortContext)
                        .replaceSort("fullName",
                                List.of("{E}.firstName", "{E}.lastName"),
                                Comparator.comparing(User::getFirstName,
                                                Comparator.nullsFirst(String::compareTo))
                                        .thenComparing(User::getLastName,
                                                Comparator.nullsFirst(String::compareTo)))
                        .build());
    }
    // end::sort-builder-computed-column[]

    // tag::full-name-renderer[]
    @Supply(to = "fullNameUsersDataGrid.fullName", subject = "renderer")
    protected Renderer<User> fullNameUsersDataGridFullNameRenderer() {
        return new TextRenderer<>(user ->
                ((user.getFirstName() == null ? "" : user.getFirstName()) + " "
                        + (user.getLastName() == null ? "" : user.getLastName())).trim());
    }
    // end::full-name-renderer[]
}
