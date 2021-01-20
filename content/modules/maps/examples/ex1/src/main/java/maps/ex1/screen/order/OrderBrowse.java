package maps.ex1.screen.order;

import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;

import javax.inject.Inject;

@UiController("mapst_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {


    // tag::tooltip[]
    @Install(to = "map.orderLayer", subject = "tooltipContentProvider")
    private String mapOrderLayerTooltipContentProvider(Order order) {
        return order.getDate().toString();
    }
    // end::tooltip[]

    @Inject
    private Screens screens;

    @Subscribe("showHeatMapBtn")
    private void onShowHeatMapBtnClick(Button.ClickEvent event) {
        screens.create(Heatmap.class, OpenMode.DIALOG).show();
    }

    @Subscribe("showOrdersCluster")
    private void onShowOrdersClusterClick(Button.ClickEvent event) {
        screens.create(Cluster.class, OpenMode.DIALOG).show();
    }
}