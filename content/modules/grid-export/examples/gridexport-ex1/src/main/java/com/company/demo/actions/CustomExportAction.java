package com.company.demo.actions;

import com.company.demo.exporters.CustomExporter;
import io.jmix.flowui.action.ActionType;
import io.jmix.gridexportflowui.action.ExportAction;
import org.springframework.context.ApplicationContext;

@ActionType(CustomExportAction.ID)
public class CustomExportAction extends ExportAction {

    public static final String ID = "myExcelExport";

    public CustomExportAction() {
        this(ID);
    }

    public CustomExportAction(String id) {
        super(id);
        setText("myExcelExport");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        withExporter(CustomExporter.class);
    }
}