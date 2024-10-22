package com.company.ex1.view.pivottableshowaction;


import com.company.ex1.entity.TipInfo;
import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.flowui.Actions;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.pivottableflowui.action.PivotTableViewBuilder;
import io.jmix.pivottableflowui.action.ShowPivotTableAction;
import io.jmix.pivottableflowui.kit.component.model.Renderer;
import io.jmix.pivottableflowui.kit.component.model.Renderers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Route(value = "pivottable-show-action-view", layout = MainView.class)
@ViewController(id = "PivottableShowActionView")
@ViewDescriptor(path = "pivottable-show-action-view.xml")
public class PivottableShowActionView extends StandardView {

    @Autowired
    private Actions actions;
    @Autowired
    private Messages messages;
    @ViewComponent
    private DataGrid<TipInfo> tipInfoesDataGrid;
    @ViewComponent
    private JmixButton showManualSetPivotTableActionButton;

    // tag::manual-set-action[]
    @Subscribe
    public void onInit(final InitEvent event) {
        ShowPivotTableAction<TipInfo> showPivotTableAction = actions.create(ShowPivotTableAction.ID);
        showPivotTableAction.setTarget(tipInfoesDataGrid);
        showManualSetPivotTableActionButton.setAction(showPivotTableAction);
        showManualSetPivotTableActionButton.setText(
                messages.getMessage(getClass(), "manualSetShowActionButton.text"));
    }
    // end::manual-set-action[]

    // tag::custom-action[]
    @Subscribe("tipInfoesDataGrid.customShowPivotTableAction")
    public void onCustomShowPivotTableAction(final ActionPerformedEvent event) {
        PivotTableViewBuilder builder = getApplicationContext().getBean(
                PivotTableViewBuilder.class, tipInfoesDataGrid);
        Renderers renderers = new Renderers();
        renderers.setSelectedRenderer(Renderer.TABLE);
        renderers.setRenderers(List.of(Renderer.TABLE, Renderer.TABLE_BAR_CHART, Renderer.HEATMAP,
                Renderer.ROW_HEATMAP, Renderer.COL_HEATMAP));

        builder.withIncludedProperties(Arrays.asList("sex", "smoker", "day", "time"))
                .withRows(Arrays.asList("sex", "smoker"))
                .withColumns(Arrays.asList("day", "time"))
                .withRenderers(renderers)
                .withItems(tipInfoesDataGrid.getItems().getItems())
                .show();
    }
    // end::custom-action[]
}