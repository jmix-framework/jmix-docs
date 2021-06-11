package ui.ex1.screen.entity.person;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Person;

@UiController("uiex1_Person.edit")
@UiDescriptor("person-edit.xml")
@EditedEntityContainer("personDc")
public class PersonEdit extends StandardEditor<Person> {
}