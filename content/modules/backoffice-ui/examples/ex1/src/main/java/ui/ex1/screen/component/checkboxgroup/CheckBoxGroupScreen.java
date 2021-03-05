package ui.ex1.screen.component.checkboxgroup;

import io.jmix.ui.component.CheckBoxGroup;
import io.jmix.ui.component.data.options.ContainerOptions;
import io.jmix.ui.component.data.options.EnumOptions;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Country;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Level;

@UiController("CheckBoxGroupScreen")
@UiDescriptor("check-box-group-screen.xml")
public class CheckBoxGroupScreen extends Screen {
    // tag::simple-check-box-1[]
    @Autowired
    private CheckBoxGroup<Level> checkBoxGroup;

    // end::simple-check-box-1[]

    // tag::countries-dc-1[]
    @Autowired
    private CheckBoxGroup<Country> countriesCheckBoxGroup;
    @Autowired
    private CollectionContainer<Country> countriesDc;
    // end::countries-dc-1[]

    // tag::countries-dl-1[]
    @Autowired
    private CollectionLoader<Country> countriesDl;

    // end::countries-dl-1[]

    // tag::customers-description-1[]
    @Autowired
    private CheckBoxGroup<Customer> checkBoxGroupCustomer;
    @Autowired
    private CollectionLoader<Customer> customersDl;

    // end::customers-description-1[]

    // tag::start-init[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::start-init[]

        // tag::enum-check-box-2[]
        checkBoxGroup.setOptionsEnum(Level.class);
        // end::enum-check-box-2[]

        // tag::options-check-box-2[]
        checkBoxGroup.setOptions(new EnumOptions<>(Level.class));
        // end::options-check-box-2[]

        // tag::countries-dc-2[]
        countriesCheckBoxGroup.setOptions(new ContainerOptions<>(countriesDc));
        // tag::countries-dl-2[]
        countriesDl.load();
        // end::countries-dl-2[]
        // end::countries-dc-2[]

        // tag::customers-description-2[]
        checkBoxGroupCustomer.setOptionDescriptionProvider(customer ->
                "Email: " + customer.getEmail());
        customersDl.load();
        // end::customers-description-2[]

        // tag::end-init[]
    }
    // end::end-init[]

    // tag::install[]
    @Install(to = "checkBoxGroup", subject = "optionDescriptionProvider")
    private String checkBoxGroupOptionDescriptionProvider(Level level) {
        switch (level) {
            case PLATINUM:
                return "Senior";
            case GOLD:
                return "Middle";
            default:
                return "Junior";
        }
    }
    // end::install[]
}