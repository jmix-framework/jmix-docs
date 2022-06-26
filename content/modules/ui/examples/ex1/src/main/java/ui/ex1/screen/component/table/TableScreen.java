package ui.ex1.screen.component.table;


import io.jmix.email.EmailAttachment;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.ItemTrackingAction;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.table.ContainerTableItems;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import io.jmix.ui.theme.ThemeClassNames;
import io.jmix.uiexport.action.ExcelExportAction;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.BudgetItem;
import ui.ex1.entity.City;
import ui.ex1.entity.Customer;
import ui.ex1.screen.actions.SendByEmailAction;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// tag::table-screen-start[]
@UiController("table-screen")
@UiDescriptor("table-screen.xml")
public class TableScreen extends Screen {

    // end::table-screen-start[]
    // tag::inject-customersTable[]
    @Autowired
    private Table<Customer> customersTable;
    // end::inject-customersTable[]
    // tag::inject-notifications[]
    @Autowired
    private Notifications notifications;

    // end::inject-notifications[]
    // tag::inject-SendByEmailAction[]
    @Named("customersTable.sendByEmail")// <1>
    private SendByEmailAction<Customer> customersTableSendByEmail;

    // end::inject-SendByEmailAction[]
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private Table<Customer> tableWithActions;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private CollectionContainer<City> citiesDc;
    @Autowired
    private Table<Customer> tableBorderless;
    @Autowired
    private CollectionContainer<Customer> customersDc;
    @Autowired
    private Table<Customer> printableTable;
    @Autowired
    private Table<Customer> customersTable1;
    // tag::inject-excel-export[]
    @Named("printableTable.excelExport")
    protected ExcelExportAction printableTableExcelExport;

    // end::inject-excel-export[]
    // tag::inject-tableClick[]
    @Autowired
    private Table<Customer> tableClick;

    // end::inject-tableClick[]

    // tag::onInit-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit-start[]

        customersTable.addGeneratedColumn("aaa", entity -> null);


        // tag::add-base-action[]
        customersTable.addAction(new AboutSingleAction());
        // end::add-base-action[]
        // tag::add-ItemTrackingAction[]
        customersTable.addAction(new ItemTrackingAction("about") {
            @Nullable
            @Override
            public String getCaption() {
                return "About";
            }

            @Override
            public void actionPerform(Component component) {
                notifications.create()
                        .withCaption("Hello " + customersTable.getSelected().iterator().next())
                        .withType(Notifications.NotificationType.TRAY)
                        .show();
            }
        });
        // end::add-ItemTrackingAction[]
        // tag::table-set-style-name[]
        tableBorderless.setStyleName(ThemeClassNames.TABLE_BORDERLESS);
        // end::table-set-style-name[]
        // tag::column-value-provider[]
        printableTableExcelExport.addColumnValueProvider("firstName", context -> { // <1>
            Customer customer = context.getEntity();
            return "Name: " + customer.getFirstName();
        });
        // end::column-value-provider[]
        printableTableExcelExport.addColumnValueProvider("fullName", context -> {
            Customer customer = context.getEntity();
            return customer.getFirstName() + " " + customer.getLastName();
        });
        // tag::table-add-printable[]
        /*printableTable.addPrintable("firstName", new Table.Printable<Customer, String>() {
            @Override
            public String getValue(Customer item) {
                return "Name: " + item.getFirstName();
            }
        });*/
        // end::table-add-printable[]
        // tag::printable-column-generator[]
        /*printableTable.addGeneratedColumn("fullName", new Table.PrintableColumnGenerator<Customer, String>() {
            @Override
            public String getValue(Customer item) {
                return item.getFirstName() + " " + item.getLastName();
            }

            @Override
            public Component generateCell(Customer entity) {
                Label label = uiComponents.create(Label.NAME);
                label.setValue(entity.getFirstName() + " " + entity.getLastName());
                return label;
            }
        });*/
        // end::printable-column-generator[]
        // tag::item-click-action[]
        tableClick.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    Customer customer = tableClick.getSingleSelected();
                    if (customer != null) {
                        notifications.create()
                                .withCaption("Item clicked for: " + customer.getFirstName()+
                                        "" + customer.getLastName())
                                .show();
                    }
                }));
        // end::item-click-action[]
        // tag::enter-press-action[]
        tableClick.setEnterPressAction(new BaseAction("enterPressAction")
                .withHandler(actionPerformedEvent -> {
                    Customer customer = tableClick.getSingleSelected();
                    if (customer != null) {
                        notifications.create()
                                .withCaption("Enter pressed for: " + customer.getFirstName()+
                                        "" + customer.getLastName())
                                .show();
                    }
                }));
        // end::enter-press-action[]

        // tag::programmatic-binding[]
        customersTable1.setItems(new ContainerTableItems<>(customersDc));
        // end::programmatic-binding[]

        // tag::onInit-end[]
    }

    // end::onInit-end[]
    // tag::base-action-table[]
    private class AboutSingleAction extends BaseAction {

        public AboutSingleAction() {
            super("aboutSingle");
        }

        @Nullable
        @Override
        public String getCaption() {
            return "About Single";
        }

        @Override
        public void actionPerform(Component component) {
            notifications.create()
                    .withCaption("Hello " + customersTable.getSingleSelected())
                    .withType(Notifications.NotificationType.TRAY)
                    .show();
        }

        @Override
        public boolean isApplicable() {
            return customersTable != null && customersTable.getSelected().size() == 1;
        }
    }

    // end::base-action-table[]
    // tag::sendByEmailAction-handlers[]
    @Subscribe("customersTable.sendByEmail")
    public void onCustomersTableSendByEmailEmailSent(SendByEmailAction.EmailSentEvent event) {// <2>
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("Email sent")
                .show();
    }

    @Install(to = "customersTable.sendByEmail", subject = "bodyGenerator")
    private String customersTableSendByEmailBodyGenerator(Customer customer) {// <3>
        return "Hello, " + customer.getFirstName();
    }

    @Install(to = "customersTable.sendByEmail", subject = "attachmentProvider")
    private List<EmailAttachment> customersTableSendByEmailAttachmentProvider(Customer customer) {// <4>
        return Collections.emptyList();
    }

    // end::sendByEmailAction-handlers[]
    // tag::empty-state-link-click-handler[]
    @Install(to = "tablePlaceholder", subject = "emptyStateLinkClickHandler")
    private void tablePlaceholderEmptyStateLinkClickHandler(
            Table.EmptyStateClickEvent<Customer> emptyStateClickEvent) {
        screenBuilders.editor(emptyStateClickEvent.getSource())
                .newEntity()
                .show();
    }

    // end::empty-state-link-click-handler[]
    // tag::aggregation-distribution-provider[]
    @Install(to = "budgetTable", subject = "aggregationDistributionProvider")
    private void budgetTableAggregationDistributionProvider(
            Table.AggregationDistributionContext<BudgetItem> context) {
        Collection<BudgetItem> scope = context.getScope();
        if (scope.isEmpty()) {
            return;
        }

        double value = context.getValue() != null ?
                ((double) context.getValue()) : 0;

        for (BudgetItem budgetItem : scope) {
            budgetItem.setAmount(value / 100 * budgetItem.getPercent());
        }
    }
    // end::aggregation-distribution-provider[]

    @Subscribe("tableWithActions.about")
    public void onTableWithActionsAbout(Action.ActionPerformedEvent event) {
        notifications.create()
                .withCaption("Simple info: " + tableWithActions.getSingleSelected())
                .withType(Notifications.NotificationType.TRAY)
                .show();
    }

    // tag::column-generator-text[]
    @Install(to = "tableGeneratedColumn.fullName", subject = "columnGenerator")
    private Component tableGeneratedColumnFullNameColumnGenerator(Customer customer) {
        return new Table.PlainTextCell(customer.getFirstName() + " " + customer.getLastName());
    }

    // end::column-generator-text[]
    // tag::column-generator[]
    @Install(to = "tableGeneratedColumn.isEmail", subject = "columnGenerator")
    private Component tableGeneratedColumnIsEmailColumnGenerator(Customer customer) {
        CheckBox isEmail = uiComponents.create(CheckBox.class);
        isEmail.setValue(customer.getEmail() != null);
        return isEmail;
    }

    // end::column-generator[]
    // tag::column-collapse-event[]
    @Subscribe("tableCollapsed")
    public void onTableCollapsedColumnCollapse(Table.ColumnCollapseEvent<Customer> event) {
        notifications.create()
                .withCaption((event.isCollapsed() ? "Collapsed: " : "Expanded: ") +
                        event.getColumn().getCaption())
                .show();
    }

    // end::column-collapse-event[]
    // tag::column-reorder-event[]
    @Subscribe("tableReorder")
    public void onTableReorderColumnReorder(Table.ColumnReorderEvent<Customer> event) {
        notifications.create()
                .withCaption("Columns were reordered!")
                .show();
    }

    // end::column-reorder-event[]
    // tag::icon-provider[]
    @Install(to = "tableWithIcons", subject = "iconProvider")
    private String tableWithIconsIconProvider(Customer customer) {
        return customer.getEmail() != null ?
                JmixIcon.MAIL_FORWARD.source() : JmixIcon.CANCEL.source();
    }

    // end::icon-provider[]
    // tag::item-description-provider[]
    @Install(to = "tableWithDescription", subject = "itemDescriptionProvider")
    private String tableWithDescriptionItemDescriptionProvider(
            Customer customer, String property) {
        if (property == null)
            return null;
        else if (property.equals("rewardPoints")) {
            return "Reward points are a part of the loyalty program affecting the rating of the " +
                    customer.getFirstName() + " " + customer.getLastName();
        } else if (property.equals("level")) {
            switch (customer.getLevel()) {
                case SILVER:
                    return "The customer gets special offers from sellers and birthday congratulations";
                case GOLD:
                    return "The customer gets 2 coupons with a rating of $ 1";
                case PLATINUM:
                    return "The customer gets a coupons with a par value of $ 3";
                case DIAMOND:
                    return "The customer gets a coupon with a par value of $ 5";
            }
        }
        return null;
    }

    // end::item-description-provider[]
    // tag::selection-event[]
    @Autowired
    private Table<Customer> tableSelectEvent;

    @Subscribe("tableSelectEvent")
    public void onTableSelectEventSelection(Table.SelectionEvent<Customer> event) {
        Customer customer = tableSelectEvent.getSingleSelected();
        notifications.create()
                .withCaption("You selected " + customer.getFirstName() +
                        " " + customer.getLastName() + " customer")
                .show();
    }

    // end::selection-event[]
    // tag::style-provider[]
    @Install(to = "styledTable", subject = "styleProvider")
    private String styledTableStyleProvider(Customer entity, String property) {
        if (property == null) {
            if (Boolean.TRUE.equals(entity.getEmail() != null)) {
                return "customer-has-email";
            }
        } else if (property.equals("level")) {
            switch (entity.getLevel()) {
                case SILVER:
                    return "level-silver";
                case GOLD:
                    return "level-gold";
                case PLATINUM:
                    return "level-platinum";
                case DIAMOND:
                    return "level-diamond";
            }
        }
        return null;
    }
    // end::style-provider[]

    // tag::lookup-select-handler[]
    @Install(to = "tableLookupSelect", subject = "lookupSelectHandler")
    private void tableLookupSelectLookupSelectHandler(Collection<Customer> collection) {
        checkCustomers(collection);
    }
    // end::lookup-select-handler[]
    private void checkCustomers(Collection<Customer> customers){
        notifications.create()
                .withCaption("Customers were selected")
                .show();
    }
    // tag::printable-table-column-generator[]
    @Install(to = "printableTable.fullName", subject = "columnGenerator")
    protected Component printableTableFullNameColumnGenerator(Customer customer) {
        return new Table.PlainTextCell(customer.getFirstName() + " " + customer.getLastName());
    }
    // end::printable-table-column-generator[]
    @Install(to = "printableTable.fullName", subject = "valueProvider")
    protected Object printableTableFullNameValueProvider(Customer customer) {
        return null;
    }

    // tag::column-generator-linked-column[]
    @Install(to = "countryTable.[city.country.name]", subject = "columnGenerator")
    private Component countryTableCityCountryNameColumnGenerator(Customer customer) {
        return new Table.PlainTextCell(customer.getCity().getCountry().getName());
    }
    // end::column-generator-linked-column[]
    // tag::table-screen-end[]
}
// end::table-screen-end[]