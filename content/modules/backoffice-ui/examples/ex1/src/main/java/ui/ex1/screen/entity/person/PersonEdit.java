package ui.ex1.screen.entity.person;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Person;

@UiController("uiex1_Person.edit")
@UiDescriptor("person-edit.xml")
@EditedEntityContainer("personDc")
public class PersonEdit extends StandardEditor<Person> {
}