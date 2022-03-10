package ui.ex1.screen.component.relatedentities;

import io.jmix.core.Metadata;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.relatedentities.RelatedEntitiesBuilder;
import io.jmix.ui.relatedentities.RelatedEntitiesSupport;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Order;

@UiController("sample_RelatedEntitiesScreen")
@UiDescriptor("related-entities-screen.xml")
public class RelatedEntitiesScreen extends Screen {
    // tag::autowired-related-entities-support[]
    @Autowired
    protected RelatedEntitiesSupport relatedEntitiesSupport;

    // end::autowired-related-entities-support[]
    // tag::autowired-orders-table[]
    @Autowired
    protected Table<Order> ordersTable;

    // end::autowired-orders-table[]

    @Autowired
    protected Metadata metadata;

    // tag::related-click[]
    @Subscribe("related")
    protected void onRelatedClick(Button.ClickEvent event) {
        RelatedEntitiesBuilder builder = relatedEntitiesSupport.builder(this);

        Screen customerBrowser = builder
                .withEntityClass(Order.class)
                .withProperty("customer")
                .withSelectedEntities(ordersTable.getSelected())
                .build();
        customerBrowser.show();
    }
    // end::related-click[]
    // tag::related-btn-click[]
    @Subscribe("relatedBtn")
    protected void onRelatedBtnClick(Button.ClickEvent event) {
        RelatedEntitiesBuilder builder = relatedEntitiesSupport.builder(this);

        Screen customerBrowser = builder
                .withMetaClass(metadata.getClass(Order.class))
                .withProperty("customer")
                .withScreenId("uiex1_Customer.browse-filter")
                .withSelectedEntities(ordersTable.getSelected())
                .withOpenMode(OpenMode.DIALOG)
                .build();
        customerBrowser.show();
    }
    // end::related-btn-click[]
}