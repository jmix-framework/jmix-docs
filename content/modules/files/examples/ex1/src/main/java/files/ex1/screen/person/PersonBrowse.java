package files.ex1.screen.person;

import files.ex1.entity.Person;
import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;

@UiController("Person.browse")
@UiDescriptor("person-browse.xml")
@LookupComponent("personsTable")
public class PersonBrowse extends StandardLookup<Person> {
}