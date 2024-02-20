package com.company.demo.exporters;

import com.vaadin.flow.component.grid.Grid;
import io.jmix.flowui.download.Downloader;
import io.jmix.gridexportflowui.exporter.DataGridExporter;
import io.jmix.gridexportflowui.exporter.ExportMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Function;

// tag::CustomExporter[]
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomExporter implements DataGridExporter {
    // end::CustomExporter[]
    @Override
    public void exportDataGrid(Downloader downloader, Grid<Object> dataGrid, ExportMode exportMode) {
        //your custom logic
    }

    @Override
    public String getLabel() {
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

    @Override
    public Function<ColumnValueContext, Object> getColumnValueProvider(String columnId) {
        //your custom logic
        return null;
    }
}