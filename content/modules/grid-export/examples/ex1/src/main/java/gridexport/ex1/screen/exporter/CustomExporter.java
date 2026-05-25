package gridexport.ex1.screen.exporter;

import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.Table;
import io.jmix.ui.download.Downloader;
import io.jmix.gridexportui.exporter.ExportMode;
import io.jmix.gridexportui.exporter.TableExporter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.function.Function;

// tag::my-custom-exporter[]
@Component("ui_CustomExporter")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomExporter implements TableExporter {
    @Override
    public void exportTable(Downloader downloader, Table<Object> table, ExportMode exportMode) {
        //your custom logic
    }

    @Override
    public void exportDataGrid(Downloader downloader, DataGrid<Object> dataGrid, ExportMode exportMode) {
        //your custom logic
    }

    @Override
    public String getCaption() {
        return "custom";
    }

    @Override
    public void addColumnValueProvider(String columnId, Function<ColumnValueContext, Object> columnValueProvider) {
        //your custom logic
    }

    @Override
    public void removeColumnValueProvider(String columnId) {
        //your custom logic
    }

    @Nullable
    @Override
    public Function<ColumnValueContext, Object> getColumnValueProvider(String columnId) {
        //your custom logic
        return null;
    }
}
// end::my-custom-exporter[]