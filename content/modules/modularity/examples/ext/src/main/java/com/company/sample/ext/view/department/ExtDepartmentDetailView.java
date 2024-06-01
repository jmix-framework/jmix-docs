package com.company.sample.ext.view.department;

import com.company.sample.base.view.department.DepartmentDetailView;
import com.company.sample.ext.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "departments/:id", layout = MainView.class)
@ViewController("base_Department.detail")
@ViewDescriptor("ext-department-detail-view.xml")
public class ExtDepartmentDetailView extends DepartmentDetailView {
}