package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.Sort;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;

@Route(value = "department-expression-order", layout = MainView.class)
@ViewController("DepartmentExpressionOrderView")
@ViewDescriptor("department-expression-order-view.xml")
public class DepartmentExpressionOrderView extends StandardView {

    // tag::expression-order[]
    @ViewComponent
    private CollectionLoader<Department> departmentsDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        departmentsDl.setSort(Sort.by(
                Sort.ExpressionOrder.asc("length({E}.name)"), // <1>
                Sort.Order.asc("name")
        ));
        departmentsDl.load(); // <2>
    }
    // end::expression-order[]
}
