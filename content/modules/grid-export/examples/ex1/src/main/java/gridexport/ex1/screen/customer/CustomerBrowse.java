package gridexport.ex1.screen.customer;

import gridexport.ex1.screen.exporter.CustomExportAction;
import gridexport.ex1.screen.exporter.CustomExporter;
import io.jmix.ui.Actions;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.screen.*;
import gridexport.ex1.entity.Customer;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.uiexport.action.ExcelExportAction;
import io.jmix.uiexport.action.JsonExportAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.inject.Named;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
    // tag::customers-table-excel-export[]
    @Named("customersTable.excel")
    protected ExcelExportAction customersTableExcel;

    // end::customers-table-excel-export[]
    // tag::customers-table-json-export[]
    @Named("customersTable.json")
    protected JsonExportAction customersTableJson;

    // end::customers-table-json-export[]
    // tag::ui-components[]
    @Autowired
    protected UiComponents uiComponents;

    // end::ui-components[]
    // tag::customers-table[]
    @Autowired
    protected Table<Customer> customersTable;

    // end::customers-table[]
    // tag::custom-btn[]
    @Autowired
    protected Button customBtn;

    // end::custom-btn[]
    // tag::application-context[]
    @Autowired
    protected ApplicationContext applicationContext;

    // end::application-context[]
    // tag::actions[]
    @Autowired
    protected Actions actions;

    // end::actions[]

    // tag::on-init-start[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::on-init-start[]
        // tag::set-caption[]
        customersTableExcel.setCaption("Export to Excel");
        // end::set-caption[]
        // tag::set-caption-json[]
        customersTableJson.setCaption("Export to JSON");
        // end::set-caption-json[]
        // tag::add-column-value-provider[]
        customersTableExcel.addColumnValueProvider("isEmail", context -> {
            Customer customer = context.getEntity();
            return customer.getEmail() != null;
        });
        // end::add-column-value-provider[]
        customersTableJson.addColumnValueProvider("isEmail", context -> {
            Customer customer = context.getEntity();
            return customer.getEmail() != null;
        });
        customersTableJson.addColumnValueProvider("age", context -> {
            Customer customer = context.getEntity();
            return "Age is " + customer.getAge();
        });
        // tag::custom-action[]
        CustomExportAction customAction = actions.create(CustomExportAction.class);

        customAction.setTableExporter(applicationContext.getBean(CustomExporter.class));
        customAction.setTarget(customersTable);
        customAction.setApplicationContext(applicationContext);

        customBtn.setAction(customAction);
        // end::custom-action[]
        // tag::on-init-end[]
    }

    // end::on-init-end[]
    // tag::generated-column[]
    @Install(to = "customersTable.isEmail", subject = "columnGenerator")
    protected Component customersTableIsEmailColumnGenerator(Customer customer) {
        CheckBox isEmail = uiComponents.create(CheckBox.class);
        isEmail.setValue(customer.getEmail() != null);
        return isEmail;
    }
    // end::generated-column[]
    @Install(to = "customersDataGrid.isEmail", subject = "columnGenerator")
    protected Boolean customersDataGridIsEmailColumnGenerator(DataGrid.ColumnGeneratorEvent<Customer> columnGeneratorEvent) {
        return columnGeneratorEvent.getItem().getEmail() != null;
    }

}