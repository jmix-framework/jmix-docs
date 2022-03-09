package ui.ex1.screen.entity.person;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Person;

@UiController("uiex1_Person.browse")
@UiDescriptor("person-browse.xml")
@LookupComponent("personsTable")
public class PersonBrowse extends StandardLookup<Person> {
}