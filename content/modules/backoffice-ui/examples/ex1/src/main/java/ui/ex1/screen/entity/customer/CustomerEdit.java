package ui.ex1.screen.entity.customer;

import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.RemoveOperation;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.list.AddAction;
import io.jmix.ui.action.list.ExcludeAction;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Brand;
import ui.ex1.entity.Customer;
import ui.ex1.screen.entity.brand.BrandBrowse;

import javax.inject.Named;
import java.util.Collection;

@UiController("uiex1_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {

    @Autowired
    private Table<Brand> brandsTable;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Notifications notifications;

    @Autowired
    private Dialogs dialogs;
    // tag::exclude-action[]
    @Named("brandsTable.exclude")
    private ExcludeAction<Brand> excludeAction;

    // end::exclude-action[]

    // tag::add-action[]
    @Named("brandsTable.add")
    private AddAction<Brand> addAction;

    // end::add-action[]
    // tag::inject-remove-operation[]
    @Autowired
    private RemoveOperation removeOperation;

    // end::inject-remove-operation[]

    // tag::on-init-start[]
    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
    // end::on-init-start[]
        // tag::add-action-set[]
        addAction.setOpenMode(OpenMode.DIALOG);
        addAction.setScreenClass(BrandBrowse.class);
        // end::add-action-set[]
        // tag::exclude-action-set[]
        excludeAction.setConfirmation(true);
        excludeAction.setConfirmationTitle("Removing brand...");
        excludeAction.setConfirmationMessage("Do you really want to remove the brand from the list?");
        // end::exclude-action-set[]
    // tag::on-init-end[]
    }
    // end::on-init-end[]

    // tag::screen-options-supplier[]
    @Install(to = "brandsTable.add", subject = "screenOptionsSupplier")
    private ScreenOptions brandsTableAddScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::screen-options-supplier[]
    // tag::screen-configurer[]
    @Install(to = "brandsTable.add", subject = "screenConfigurer")
    private void brandsTableAddScreenConfigurer(Screen screen) {
        ((BrandBrowse) screen).setSomeParameter(10);
    }
    // end::screen-configurer[]
    // tag::select-validator[]
    @Install(to = "brandsTable.add", subject = "selectValidator")
    private boolean brandsTableAddSelectValidator(LookupScreen.ValidationContext<Brand> validationContext) {
        boolean valid = checkBrands(validationContext.getSelectedItems());
        if (!valid) {
            notifications.create().withCaption("Selection is not valid").show();
        }
        return valid;
    }
    // end::select-validator[]
    private boolean checkBrands(Collection<Brand> brands){
        return true;
    }

    private Collection<Brand> reloadBrands(Collection<Brand> brands){
        return brands;
    }

    // tag::transformation[]
    @Install(to = "brandsTable.add", subject = "transformation")
    private Collection<Brand> brandsTableAddTransformation(Collection<Brand> collection) {
        return reloadBrands(collection);
    }
    // end::transformation[]

    // tag::after-close-handler[]
    @Install(to = "brandsTable.add", subject = "afterCloseHandler")
    private void brandsTableAddAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.SELECT)) {
            System.out.println("Selected");
        }
    }
    // end::after-close-handler[]


    // tag::action-performed-event[]
    @Subscribe("brandsTable.add")
    public void onBrandsTableAdd(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to add a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> addAction.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::action-performed-event[]

    // tag::action-performed-event-2[]
    @Subscribe("brandsTable.addAction")
    public void onBrandsTableAddAction(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(brandsTable)
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(BrandBrowse.class)
                .withSelectValidator(customerValidationContext -> {
                    boolean valid = checkBrands(customerValidationContext.getSelectedItems());
                    if (!valid) {
                        notifications.create().withCaption("Selection is not valid").show();
                    }
                    return valid;

                })
                .build()
                .show();
    }
    // end::action-performed-event-2[]

    public void setSomeParameter(int param){
        int i = param;
    }
    // tag::exclude-after-action-performed-handler[]
    @Install(to = "brandsTable.exclude", subject = "afterActionPerformedHandler")
    private void brandsTableExcludeAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<Brand> event) {
        System.out.println("Removed " + event.getItems());
    }
    // end::exclude-after-action-performed-handler[]
    // tag::exclude-action-cancelled-handler[]
    @Install(to = "brandsTable.exclude", subject = "actionCancelledHandler")
    private void brandsTableExcludeActionCancelledHandler(RemoveOperation.ActionCancelledEvent<Brand> event) {
        System.out.println("Cancelled");
    }
    // end::exclude-action-cancelled-handler[]

    // tag::exclude-action-performed-event[]
    @Subscribe("brandsTable.exclude")
    public void onBrandsTableExclude(Action.ActionPerformedEvent event) {
        excludeAction.setConfirmation(false);
        dialogs.createOptionDialog()
                .withCaption("My confirm dialog")
                .withMessage("Do you really want to remove the brand from the list?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> excludeAction.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::exclude-action-performed-event[]
    // tag::exclude-action-performed-event-2[]
    @Subscribe("brandsTable.excludeAction")
    public void onBrandsTableExcludeAction(Action.ActionPerformedEvent event) {
        removeOperation.builder(brandsTable)
                .withConfirmationMessage("Do you really want to remove the brand from the list?")
                .withConfirmationTitle("Removing brand...")
                .exclude();
    }
    // end::exclude-action-performed-event-2[]
}