package ui.ex1.screen.entity.customer;

import io.jmix.core.Metadata;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.list.RelatedAction;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.relatedentities.RelatedEntitiesBuilder;
import io.jmix.ui.relatedentities.RelatedEntitiesSupport;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Brand;
import ui.ex1.entity.Customer;
import ui.ex1.screen.entity.brand.BrandBrowse;

import javax.inject.Named;

@UiController("uiex1_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
    @Autowired
    private RelatedEntitiesSupport relatedEntitiesSupport;
    @Autowired
    private Metadata metadata;
    @Autowired
    private GroupTable<Customer> customersTable;
    // tag::inject-relatedAction[]
    @Named("customersTable.related")
    private RelatedAction relatedAction;

    // end::inject-relatedAction[]

    public void setSomeParameter(int param){
        int i = param;
    }

    // tag::onInit-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit-start[]
        // tag::relatedAction-set[]
        relatedAction.setOpenMode(OpenMode.DIALOG);
        relatedAction.setProperty("favoriteBrands");
        // end::relatedAction-set[]
        // tag::onInit-end[]
    }
    // end::onInit-end[]
    // tag::related-screen-options-supplier[]
    @Install(to = "customersTable.related", subject = "screenOptionsSupplier")
    private ScreenOptions customersTableRelatedScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::related-screen-options-supplier[]
    // tag::related-screen-configurer[]
    @Install(to = "customersTable.related", subject = "screenConfigurer")
    private void customersTableRelatedScreenConfigurer(Screen screen) {
        ((BrandBrowse) screen).setSomeParameter(10);
    }
    // end::related-screen-configurer[]
    // tag::related-after-close-handler[]
    @Install(to = "customersTable.related", subject = "afterCloseHandler")
    private void customersTableRelatedAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        System.out.println("Closed with " + afterCloseEvent.getCloseAction());
    }
    // end::related-after-close-handler[]

    // tag::related-action-performed-event[]
    @Subscribe("customersTable.related")
    public void onCustomersTableRelated(Action.ActionPerformedEvent event) {
        RelatedEntitiesBuilder builder = relatedEntitiesSupport.builder(this);
        Screen brandBrowser = builder
                .withMetaClass(metadata.getClass(Customer.class))
                .withProperty("favoriteBrands")
                .withSelectedEntities(customersTable.getSelected())
                .withConfigurationName("See favourite brands")
                .build();
        brandBrowser.show();
    }
    // end::related-action-performed-event[]
}