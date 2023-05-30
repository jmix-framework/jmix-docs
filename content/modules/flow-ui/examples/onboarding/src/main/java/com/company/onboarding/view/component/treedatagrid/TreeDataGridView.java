package com.company.onboarding.view.component.treedatagrid;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.treegrid.CollapseEvent;
import com.vaadin.flow.component.treegrid.ExpandEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.TreeDataGrid;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "TreeDataGridView", layout = MainView.class)
@ViewController("TreeDataGridView")
@ViewDescriptor("tree-data-grid-view.xml")
public class TreeDataGridView extends StandardView {
    @Subscribe("departmentsTable")
    public void onDepartmentsTableCollapse(CollapseEvent<Object, TreeDataGrid> event) {
        
    }

    @Subscribe("departmentsTable")
    public void onDepartmentsTableExpand(ExpandEvent<Object, TreeDataGrid> event) {
        
    }
}
