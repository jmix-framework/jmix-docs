package ui.ex1.screen.screens.open;

import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Level;
import ui.ex1.screen.entity.customer.CustomerEdit;

@UiController("sample_ShowScreens")
@UiDescriptor("show-screens.xml")
@LookupComponent("customersTable")
public class ShowScreens extends Screen {

    // tag::entity-picker[]
    @Autowired
    private EntityPicker customerEntityPicker;

    // end::entity-picker[]

    // tag::text-field[]
    @Autowired
    private TextField userField;

    // end::text-field[]

    // tag::group-table[]
    @Autowired
    private GroupTable<Customer> customersTable;

    // end::group-table[]

    // tag::inject-screens[]
    @Autowired
    private Screens screens;

    // end::inject-screens[]

    // tag::screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::screen-builders[]

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::show-screen[]
    private void showFancyScreen(String message) {
        FancyMessageScreen fancyScreen = screens.create(FancyMessageScreen.class); // <1>
        fancyScreen.setFancyMessage(message); // <2>
        screens.show(fancyScreen); // <3>
    }
    // end::show-screen[]

    // tag::show-line[]
    private void showDefaultFancyScreen() {
        screens.create(FancyMessageScreen.class).show();
    }
    // end::show-line[]

    // tag::show-other-screen[]
    private void openOtherScreen() {
        screenBuilders.screen(this)
                .withScreenClass(OtherScreen.class)
                .withAfterCloseListener(e -> {
                    notifications.create().withCaption("Closed").show();
                })
                .build()
                .show();
    }
    // end::show-other-screen[]

    // tag::edit-customer[]
    private void editSelectedEntity(Customer entity) {
        screenBuilders.editor(Customer.class, this)
                .editEntity(entity)
                .build()
                .show();
    }
    // end::edit-customer[]

    // tag::edit-from-table[]
    private void editSelectedEntity() {
        screenBuilders.editor(customersTable).build().show();
    }
    // end::edit-from-table[]

    // tag::create[]
    private void createNewEntity() {
        screenBuilders.editor(customersTable)
                .newEntity()
                .build()
                .show();
    }
    // end::create[]

    // tag::create-with-parameter[]
    private void createNewEntityWithParameter() {
        screenBuilders.editor(customersTable)
                .newEntity()
                .withInitializer(customer -> { // <1>
                    customer.setLevel(Level.SILVER);
                })
                .withScreenClass(CustomerEdit.class) // <2>
                .withOpenMode(OpenMode.DIALOG) // <3>
                .build()
                .show();
    }
    // end::create-with-parameter[]

    // tag::lookup[]
    private void lookupCustomer() {
        screenBuilders.lookup(Customer.class, this)
                .withSelectHandler(customers -> {
                    Customer customer = customers.iterator().next();
                    userField.setValue(customer.getFirstName() + " " + customer.getLastName());
                })
                .build()
                .show();
    }
    // end::lookup[]

    // tag::lookup-select[]
    private void lookupCustomerSelect() {
        screenBuilders.lookup(Customer.class, this)
                .withField(customerEntityPicker)
                .build()
                .show();
    }
    // end::lookup-select[]

    // tag::lookup-with-parameter[]
    private void lookupCustomerWithParameter() {
        screenBuilders.lookup(Customer.class, this)
                .withScreenId("uiex1_Customer.browse")
                .withOpenMode(OpenMode.DIALOG)
                .withSelectHandler(users -> {
                    Customer customer = users.iterator().next();
                    userField.setValue(customer.getFirstName() + " " + customer.getLastName());
                })
                .build()
                .show();
    }
    // end::lookup-with-parameter[]

    @Subscribe("showFancyBtn")
    public void onShowFancyBtnClick(Button.ClickEvent event) {
        showFancyScreen("Hello");
    }

    @Subscribe("showFancyDefaultBtn")
    public void onShowFancyDefaultBtnClick(Button.ClickEvent event) {
        showDefaultFancyScreen();
    }

    @Subscribe("showOtherScreenBtn")
    public void onShowOtherScreenBtnClick(Button.ClickEvent event) {
        openOtherScreen();
    }

    @Subscribe("showEntityBtn")
    public void onShowEntityBtnClick(Button.ClickEvent event) {
        if (!(customersTable.getSingleSelected() == null)) {
            editSelectedEntity(customersTable.getSingleSelected());
        }
    }

    @Subscribe("showEntityFromTableBtn")
    public void onShowEntityFromTableBtnClick(Button.ClickEvent event) {
        if (!(customersTable.getSingleSelected() == null)) {
            editSelectedEntity();
        }
    }

    @Subscribe("showCreateEntityBtn")
    public void onShowCreateEntityBtnClick(Button.ClickEvent event) {
        createNewEntity();
    }

    @Subscribe("showCreateEntityWithParameterBtn")
    public void onShowCreateEntityWithParameterBtnClick(Button.ClickEvent event) {
        createNewEntityWithParameter();
    }

    @Subscribe("showCustomersBtn")
    public void onShowCustomersBtnClick(Button.ClickEvent event) {
        lookupCustomer();
    }

    @Subscribe("showCustomersSelectBtn")
    public void onShowCustomersSelectBtnClick(Button.ClickEvent event) {
        lookupCustomerSelect();
    }

    @Subscribe("showCustomersWithParameterBtn")
    public void onShowCustomersWithParameterBtnClick(Button.ClickEvent event) {
        lookupCustomerWithParameter();
    }
}