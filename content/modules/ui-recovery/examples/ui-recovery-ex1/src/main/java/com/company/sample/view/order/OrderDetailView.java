package com.company.sample.view.order;

import com.company.sample.entity.Order;
import com.company.sample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.view.*;
import io.jmix.uirecoveryflowui.facet.DraftRestoreContext;
import io.jmix.uirecoveryflowui.facet.DraftsFacet;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.OffsetDateTime;

@Route(value = "orders/:id", layout = MainView.class)
@ViewController(id = "Order_.detail")
@ViewDescriptor(path = "order-detail-view.xml")
@EditedEntityContainer("orderDc")
public class OrderDetailView extends StandardDetailView<Order> {

    // tag::restore-delegate[]
    @ViewComponent
    private DraftsFacet draftsFacet;

    @Autowired
    private TimeSource timeSource;

    @Install(to = "draftsFacet", subject = "restoreDelegate")
    private void draftsFacetRestoreDelegate(final DraftRestoreContext context) {
        OffsetDateTime draftDate = context.getDraft().getCreatedDate();
        OffsetDateTime minDate = timeSource.now().toOffsetDateTime().minus(Duration.ofDays(1));
        if (draftDate.isBefore(minDate)) {
            context.preventRestore(); // <1>
            draftsFacet.deleteDrafts(); // <2>
        }
    }
    // end::restore-delegate[]
}
