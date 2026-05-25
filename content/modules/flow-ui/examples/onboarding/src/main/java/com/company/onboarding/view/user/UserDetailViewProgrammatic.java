package com.company.onboarding.view.user;

import com.company.onboarding.entity.Department;
import com.company.onboarding.entity.User;
import com.company.onboarding.entity.UserStep;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.data.grid.ContainerDataGridItems;
import io.jmix.flowui.data.value.ContainerValueSource;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::progr[]
@Route(value = "users2/:id", layout = MainView.class)
@ViewController("User.detail2")
public class UserDetailViewProgrammatic extends StandardDetailView<User> {

    @Autowired
    private DataComponents dataComponents; // <1>
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FetchPlans fetchPlans;
    @Autowired
    private Metadata metadata;

    private InstanceContainer<User> userDc;
    private InstanceLoader<User> userDl;
    private CollectionPropertyContainer<UserStep> stepsDc;
    private CollectionContainer<Department> departmentsDc;
    private CollectionLoader<Department> departmentsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        createDataComponents();
        createUiComponents();
    }

    private void createDataComponents() {
        DataContext dataContext = dataComponents.createDataContext();
        getViewData().setDataContext(dataContext); // <2>

        userDc = dataComponents.createInstanceContainer(User.class);

        userDl = dataComponents.createInstanceLoader();
        userDl.setContainer(userDc); // <3>
        userDl.setDataContext(dataContext); // <4>

        FetchPlan userFetchPlan = fetchPlans.builder(User.class)
                .addFetchPlan(FetchPlan.BASE)
                .add("department", FetchPlan.BASE)
                .add("steps", FetchPlan.BASE)
                .add("steps.step", FetchPlan.BASE)
                .build();
        userDl.setFetchPlan(userFetchPlan);

        stepsDc = dataComponents.createCollectionContainer(
                UserStep.class, userDc, "steps"); // <5>

        departmentsDc = dataComponents.createCollectionContainer(Department.class);

        departmentsDl = dataComponents.createCollectionLoader();
        departmentsDl.setContainer(departmentsDc);
        departmentsDl.setDataContext(dataContext);
        departmentsDl.setQuery("select e from Department e"); // <6>
        departmentsDl.setFetchPlan(FetchPlan.BASE);
    }

    private void createUiComponents() {
        TypedTextField<String> usernameField = uiComponents.create(TypedTextField.class);
        usernameField.setValueSource(new ContainerValueSource<>(userDc, "username")); // <7>
        getContent().add(usernameField);

        FormLayout formLayout = uiComponents.create(FormLayout.class);
        getContent().add(formLayout);

        TypedTextField<String> firstNameField = uiComponents.create(TypedTextField.class);
        firstNameField.setValueSource(new ContainerValueSource<>(userDc, "firstName"));
        formLayout.add(firstNameField);

        TypedTextField<String> lastNameField = uiComponents.create(TypedTextField.class);
        lastNameField.setValueSource(new ContainerValueSource<>(userDc, "lastName"));
        formLayout.add(lastNameField);

        EntityComboBox<Department> departmentField = uiComponents.create(EntityComboBox.class);
        departmentField.setValueSource(new ContainerValueSource<>(userDc, "department"));
        departmentField.setItems(departmentsDc); // <8>
        formLayout.add(departmentField);

        DataGrid<UserStep> dataGrid = uiComponents.create(DataGrid.class);
        dataGrid.addColumn(metadata.getClass(UserStep.class).getPropertyPath("step.name"));
        dataGrid.setItems(new ContainerDataGridItems<>(stepsDc)); // <9>
        getContent().add(dataGrid);
        getContent().expand(dataGrid);

        Button okButton = uiComponents.create(Button.class);
        okButton.setText("OK");
        okButton.addClickListener(clickEvent -> closeWithSave());
        getContent().add(okButton);

        Button cancelButton = uiComponents.create(Button.class);
        cancelButton.setText("Cancel");
        cancelButton.addClickListener(clickEvent -> closeWithDiscard());
        getContent().add(cancelButton);
    }

    @Override
    protected InstanceContainer<User> getEditedEntityContainer() { // <10>
        return userDc;
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) { // <11>
        userDl.load();
        departmentsDl.load();
    }
}
// end::progr[]
