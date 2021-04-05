package ui.ex1.screen.entity.customer;

import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.list.AddAction;
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
    private Table<Brand> favoriteBrandsTable;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Notifications notifications;

    @Autowired
    private Dialogs dialogs;

    // tag::add-action[]
    @Named("favoriteBrandsTable.add")
    private AddAction<Brand> addAction;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
        addAction.setOpenMode(OpenMode.DIALOG);
        addAction.setScreenClass(BrandBrowse.class);
    }
    // end::add-action[]
    // tag::screen-options-supplier[]
    @Install(to = "favoriteBrandsTable.add", subject = "screenOptionsSupplier")
    private ScreenOptions favoriteBrandsTableAddScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::screen-options-supplier[]
    // tag::screen-configurer[]
    @Install(to = "favoriteBrandsTable.add", subject = "screenConfigurer")
    private void favoriteBrandsTableAddScreenConfigurer(Screen screen) {
        ((BrandBrowse) screen).setSomeParameter(10);
    }
    // end::screen-configurer[]
    // tag::select-validator[]
    @Install(to = "favoriteBrandsTable.add", subject = "selectValidator")
    private boolean favoriteBrandsTableAddSelectValidator(LookupScreen.ValidationContext<Brand> validationContext) {
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
    @Install(to = "favoriteBrandsTable.add", subject = "transformation")
    private Collection<Brand> favoriteBrandsTableAddTransformation(Collection<Brand> collection) {
        return reloadBrands(collection);
    }
    // end::transformation[]

    // tag::after-close-handler[]
    @Install(to = "favoriteBrandsTable.add", subject = "afterCloseHandler")
    private void favoriteBrandsTableAddAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.SELECT)) {
            System.out.println("Selected");
        }
    }
    // end::after-close-handler[]


    // tag::action-performed-event[]
    @Subscribe("favoriteBrandsTable.add")
    public void onFavoriteBrandsTableAdd(Action.ActionPerformedEvent event) {
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
    @Subscribe("favoriteBrandsTable.addAction")
    public void onFavoriteBrandsTableAddAction(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(favoriteBrandsTable)
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
}