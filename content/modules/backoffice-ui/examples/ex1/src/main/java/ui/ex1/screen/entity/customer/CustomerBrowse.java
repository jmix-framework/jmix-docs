package ui.ex1.screen.entity.customer;

import io.jmix.core.Metadata;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.Dialogs;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.list.RelatedAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlRouting;
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
// tag::route[]
@Route("customers")
// end::route[]
// tag::customer-browse-start[]
public class CustomerBrowse extends StandardLookup<Customer> {
    // end::customer-browse-start[]
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
    // tag::inject-url-routing[]
    @Autowired
    protected UrlRouting urlRouting;

    // end::inject-url-routing[]
    // tag::inject-dialogs[]
    @Autowired
    protected Dialogs dialogs;

    // end::inject-dialogs[]

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
    // tag::get-editor-route[]
    @Subscribe("getLinkButton")
    protected void onGetLinkButtonClick(Button.ClickEvent event) {
        Customer selectedCustomer = customersTable.getSingleSelected();
        if (selectedCustomer != null) {
            String routeToSelectedRole = urlRouting.getRouteGenerator()
                    .getEditorRoute(selectedCustomer);

            dialogs.createMessageDialog()
                    .withCaption("Generated route")
                    .withMessage(routeToSelectedRole)
                    .withWidth("710")
                    .show();
        }
    }
    // end::get-editor-route[]
    // tag::customer-browse-end[]
}
// end::customer-browse-end[]