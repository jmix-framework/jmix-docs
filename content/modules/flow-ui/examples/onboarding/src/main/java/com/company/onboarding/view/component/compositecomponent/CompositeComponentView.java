package com.company.onboarding.view.component.compositecomponent;

import com.company.onboarding.entity.Address;
import com.company.onboarding.entity.Employee;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::screen-controller[]
@ViewController("composite-component")
@ViewDescriptor("composite-component.xml")
public class CompositeComponentView extends StandardView {

    @ViewComponent
    protected InstanceContainer<Address> addressDc;
    @ViewComponent
    protected InstanceContainer<Employee> employeeDc;
    @ViewComponent
    protected JmixTabSheet tabSheet;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        initAddress();
    }

    protected void initAddress() {
        AddressComponent addressComponent = uiComponents.create(AddressComponent.class);
        addressComponent.setDataContainer(addressDc);

        tabSheet.add("Programmatically added component", addressComponent);
    }

    @Subscribe("employeeSelect")
    protected void onEmployeeSelectValueChange(ComponentValueChangeEvent<JmixSelect<Employee>, Employee> event) {
        employeeDc.setItem(event.getValue());
    }
}
// end::screen-controller[]