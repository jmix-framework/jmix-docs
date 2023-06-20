package gridexport.ex1.screen.exporter;

import io.jmix.ui.action.ActionType;
import io.jmix.gridexportui.action.ExportAction;
import org.springframework.context.ApplicationContext;

// tag::my-custom-action[]
@ActionType(CustomExportAction.ID)
public class CustomExportAction extends ExportAction {
    public static final String ID = "myExcelExport";

    public CustomExportAction(String id) {
        this(id, null);
    }

    public CustomExportAction() {
        this(ID);
    }

    public CustomExportAction(String id, String shortcut) {
        super(id, shortcut);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        withExporter(CustomExporter.class); // <1>
    }

    @Override
    public String getIcon() {
        return "font-icon:CHECK";
    }
}
// end::my-custom-action[]