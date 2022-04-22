package datamodel.ex1.service;

import datamodel.ex1.entity.Person;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service(PersonApiService.NAME)
public class PersonApiServiceBean implements PersonApiService{
    @Autowired
    private DataManager dataManager;

    @Override
    public List<Person> getPersons() {
        return dataManager.load(Person.class).all().list();
    }

    @Override
    public void addNewPerson(String firstName, BigDecimal height, String passportNumber) {
        Person person = dataManager.create(Person.class);
        person.setFirstName(firstName);
        person.setHeight(height);
        person.setPassportNumber(passportNumber);

        dataManager.save(person);
    }

    @Override
    public String validatePerson(String comment, Person person) {
        return null;
    }
}
