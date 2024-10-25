package com.company.ex1.view.derivedproperty;


import com.company.ex1.entity.TipInfo;
import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.pivottableflowui.kit.event.PivotTableCellClickEvent;
import io.jmix.pivottableflowui.kit.event.PivotTableRefreshEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "derived-property-view", layout = MainView.class)
@ViewController(id = "DerivedPropertyView")
@ViewDescriptor(path = "derived-property-view.xml")
public class DerivedPropertyView extends StandardView {
    @Autowired
    private Notifications notifications;

    @Subscribe("pivotTable")
    public void onPivotTablePivotTableCellClick(final PivotTableCellClickEvent<?> event) {
        List<TipInfo> items = event.getDetail().getItems();
        StringBuilder notificationMessage = new StringBuilder();
        items.forEach(tipInfo -> {
            notificationMessage.append("id: ")
                    .append(tipInfo.getId())
                    .append(", Tip: ")
                    .append(tipInfo.getTip())
                    .append("\n");
        });
        notificationMessage.deleteCharAt(notificationMessage.length() - 1);
        notifications.show("Cell click event (items in the cell)", notificationMessage.toString());
    }

    @Subscribe("pivotTable")
    public void onPivotTablePivotTableRefresh(final PivotTableRefreshEvent event) {
        
    }
}