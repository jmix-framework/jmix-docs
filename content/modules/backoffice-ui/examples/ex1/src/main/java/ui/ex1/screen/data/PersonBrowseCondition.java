package ui.ex1.screen.data;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Person;
import ui.ex1.screen.entity.person.PersonEdit;
import ui.ex1.screen.screens.open.FancyMessageScreen;

@UiController("uiex1_PersonCondition.browse")
@UiDescriptor("person-browse-condition.xml")
@LookupComponent("personsTable")
public class PersonBrowseCondition extends StandardLookup<Customer> {

    @Autowired
    private CollectionContainer<Person> personsDc;
    // tag::loader[]
    @Autowired
    private CollectionLoader<Person> personsDl;

    // end::loader[]

    // tag::screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::screen-builders[]

    // tag::data-context[]
    @Autowired
    private DataContext dataContext;

    // end::data-context[]

    // tag::screens[]
    @Autowired
    private Screens screens;

    // end::screens[]

    // tag::condition[]
    @Subscribe("nameFilterField")
    public void onNameFilterFieldValueChange1(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null) {
            personsDl.setParameter("name", "(?i)%" + event.getValue() + "%");
        } else {
            personsDl.removeParameter("name");
        }
        personsDl.load();
    }

    @Subscribe("statusFilterField")
    public void onStatusFilterFieldValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
            personsDl.setParameter("status", true);
        } else {
            personsDl.removeParameter("status");
        }
        personsDl.load();
    }
    // end::condition[]

    private Screen showFancyMessage(String message) {
        FancyMessageScreen screen = screens.create(FancyMessageScreen.class);
        screen.setFancyMessage(message);
        return screen;
    }

    // tag::person-edit[]
    @Subscribe("opentBtn")
    public void onOpentBtnClick(Button.ClickEvent event) {
        PersonEdit personEdit = screenBuilders.editor(Person.class, this)
                .withScreenClass(PersonEdit.class)
                .withParentDataContext(dataContext)
                .build();
        personEdit.show();
    }
    // end::person-edit[]


    // tag::sample-screen[]
    private void openSmplScreenWithCurrentDataContextAsParent() {
        SmplScreen smplScreen = screens.create(SmplScreen.class);
        smplScreen.setParentDataContext(dataContext);
        smplScreen.show();
    }
    // end::sample-screen[]

    @Subscribe("openSampleBtn")
    public void onOpenSampleBtnClick(Button.ClickEvent event) {
        openSmplScreenWithCurrentDataContextAsParent();
    }
}