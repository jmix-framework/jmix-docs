package com.company.ex1.view.pivottableexport;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.pivottableflowui.component.PivotTable;
import io.jmix.pivottableflowui.export.PivotTableExcelExporter;
import io.jmix.pivottableflowui.export.PivotTableExporter;
import io.jmix.pivottableflowui.export.PivotTableExporterImpl;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "pivottable-export-view", layout = MainView.class)
@ViewController(id = "PivottableExportView")
@ViewDescriptor(path = "pivottable-export-view.xml")
public class PivottableExportView extends StandardView {

    @ViewComponent
    private PivotTable<?> pivotTable;

    private PivotTableExporter pivotTableExport;

    @Subscribe
    protected void onInit(InitEvent event) {

        // tag::export[]
        PivotTableExcelExporter pivotTableExcelExporter = getApplicationContext().getBean(PivotTableExcelExporter.class);
        pivotTableExport = getApplicationContext().getBean(PivotTableExporter.class, pivotTable, pivotTableExcelExporter);
        // end::export[]
    }

    // tag::run-export[]
    @Subscribe(id = "exportButton", subject = "clickListener")
    public void onExportButtonClick(final ClickEvent<JmixButton> event) {
        pivotTableExport.exportTableToXls();
    }
    // end::run-export[]
}