package ui.ex1.screen.component.checkboxgroup;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.CheckBoxGroup;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.component.data.options.ContainerOptions;
import io.jmix.ui.component.data.options.EnumOptions;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.*;

import java.util.Collection;

@UiController("CheckBoxGroupScreen")
@UiDescriptor("check-box-group-screen.xml")
public class CheckBoxGroupScreen extends Screen {
    @Autowired
    protected CheckBoxGroup<Customer> validatedCheckBoxGroup;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected CheckBoxGroup<EducationalStage> validCheckBoxGroup;
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

        // tag::end-init[]
    }
    // end::end-init[]

    @Subscribe("validateBtn")
    protected void onValidateBtnClick(Button.ClickEvent event) {
        validatedCheckBoxGroup.validate();
    }

    // tag::option-description-provider[]
    @Install(to = "checkBoxGroupDesc", subject = "optionDescriptionProvider")
    protected String checkBoxGroupDescOptionDescriptionProvider(Customer customer) {
        return "Email: " + customer.getEmail();
    }
    // end::option-description-provider[]

    // tag::validator[]
    @Install(to = "validCheckBoxGroup", subject = "validator")
    protected void validCheckBoxGroupValidator(Collection<EducationalStage> value) {
        if (value.contains(EducationalStage.NO) & value.size() > 1)
            throw new ValidationException("You cannot select the No Education " +
                    "value together with other values");
    }
    // end::validator[]

    @Subscribe("validateBtn2")
    protected void onValidateBtn2Click(Button.ClickEvent event) {
        validCheckBoxGroup.validate();
    }
}