package com.company.onboarding.view.pivottablecustomdatabinding;


import com.company.onboarding.model.ShapeListPivotTableItems;
import com.company.onboarding.model.Shape;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.pivottableflowui.component.PivotTable;

import java.util.List;

@Route(value = "pivot-table-custom-data-binding-view", layout = MainView.class)
@ViewController(id = "PivotTableCustomDataBindingView")
@ViewDescriptor(path = "pivot-table-custom-data-binding-view.xml")
public class PivotTableCustomDataBindingView extends StandardView {

    @ViewComponent
    private PivotTable<Shape> pivotTable;

    // tag::create-items[]
    @Subscribe
    public void onInit(final InitEvent event) {
        pivotTable.setItems(new ShapeListPivotTableItems(List.of(
                new Shape(1L, "Circle", "Blue", "Middle"),
                new Shape(2L, "Circle", "Green", "Small"),
                new Shape(3L, "Ellipse", "Yellow", "Small"),
                new Shape(4L, "Ellipse", "Green", "Big"),
                new Shape(5L, "Square", "Blue", "Middle"),
                new Shape(6L, "Square", "Green", "Big"),
                new Shape(7L, "Rhombus", "Blue", "Big"),
                new Shape(8L, "Rhombus", "Yellow", "Small"),
                new Shape(8L, "Circle", "Yellow", "Small"),
                new Shape(8L, "Square", "Green", "Small"))));
    }
    // end::create-items[]
}