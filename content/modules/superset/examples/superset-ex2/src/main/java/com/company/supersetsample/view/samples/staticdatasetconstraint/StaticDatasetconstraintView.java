package com.company.supersetsample.view.samples.staticdatasetconstraint;

import com.company.supersetsample.app.DepartmentDatasetConstraintProvider;
import com.company.supersetsample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.supersetflowui.component.dataconstraint.DatasetConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "static-datasetconstraint-view", layout = MainView.class)
@ViewController("StaticDatasetconstraintView")
@ViewDescriptor("static-datasetconstraint-view.xml")
public class StaticDatasetconstraintView extends StandardView {
    // tag::dataset-constraint-provider[]
    @Autowired
    private DepartmentDatasetConstraintProvider departmentDatasetConstraintProvider;

    @Install(to = "dashboard", subject = "datasetConstraintsProvider")
    private List<DatasetConstraint> dashboardDatasetConstraintsProvider() {
        return departmentDatasetConstraintProvider.getConstraints();
    }
    // end::dataset-constraint-provider[]
}