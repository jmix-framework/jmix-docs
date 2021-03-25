package ui.ex1.screen.component.checkboxgroup;

import io.jmix.ui.component.CheckBoxGroup;
import io.jmix.ui.component.data.options.ContainerOptions;
import io.jmix.ui.component.data.options.EnumOptions;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.*;

@UiController("CheckBoxGroupScreen")
@UiDescriptor("check-box-group-screen.xml")
public class CheckBoxGroupScreen extends Screen {
    // tag::simple-check-box-1[]
    @Autowired
    private CheckBoxGroup<Operation> checkBoxGroup;

    // end::simple-check-box-1[]

    // tag::countries-dc-1[]
    @Autowired
    private CheckBoxGroup<Country> countriesCheckBoxGroup;
    @Autowired
    private CollectionContainer<Country> countriesDc;

    // end::countries-dc-1[]

    // tag::customers-description-1[]
    @Autowired
    private CheckBoxGroup<Customer> checkBoxGroupCustomer;

    // end::customers-description-1[]

    // tag::start-init[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::start-init[]

        // tag::options-check-box-2[]
        checkBoxGroup.setOptions(new EnumOptions<>(Operation.class));
        // end::options-check-box-2[]

        // tag::countries-dc-2[]
        countriesCheckBoxGroup.setOptions(new ContainerOptions<>(countriesDc));
        // end::countries-dc-2[]

        // tag::customers-description-2[]
        checkBoxGroupCustomer.setOptionDescriptionProvider(customer ->
                "Email: " + customer.getEmail());
        // end::customers-description-2[]

        // tag::end-init[]
    }
    // end::end-init[]

    // tag::install[]
    @Install(to = "checkBoxGroup", subject = "optionDescriptionProvider")
    private String checkBoxGroupOptionDescriptionProvider(Level level) {
        switch (level) {
            case PLATINUM:
                return "Platinum level";
            case GOLD:
                return "Gold level";
            default:
                return "Silver level";
        }
    }
    // end::install[]
}