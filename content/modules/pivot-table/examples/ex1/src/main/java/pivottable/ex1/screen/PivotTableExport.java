package pivottable.ex1.screen;

import io.jmix.pivottable.component.PivotTable;
import io.jmix.pivottable.component.PivotTableExtension;
import io.jmix.pivottable.component.impl.PivotExcelExporter;
import io.jmix.pivottable.component.impl.PivotTableExtensionImpl;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("sample_PivotTableExport")
@UiDescriptor("pivot-table-export.xml")
public class PivotTableExport extends Screen {

    // tag::export[]
    @Autowired
    private PivotTable pivotTable;

    @Autowired
    protected ObjectProvider<PivotExcelExporter> excelExporterObjectProvider;

    // end::export[]

    // tag::extension[]
    private PivotTableExtension extension;

    // end::extension[]

    // tag::init[]
    @Subscribe
    protected void onInit(InitEvent event) {
        extension = new PivotTableExtensionImpl(pivotTable, excelExporterObjectProvider.getObject());
    }
    // end::init[]

    // tag::button1[]
    @Subscribe("exportBtn")
    protected void onExportBtnClick(Button.ClickEvent event) {
        // end::button1[]
        // tag::set-file-name[]
        extension.setFileName("Tips");
        // end::set-file-name[]
        // tag::button2[]
        extension.exportTableToXls();
    }
    // end::button2[]

}