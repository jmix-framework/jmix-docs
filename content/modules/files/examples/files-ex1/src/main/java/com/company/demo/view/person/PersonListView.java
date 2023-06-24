package com.company.demo.view.person;

import com.company.demo.entity.Person;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "persons", layout = MainView.class)
@ViewController("Person.list")
@ViewDescriptor("person-list-view.xml")
@LookupComponent("personsDataGrid")
@DialogMode(width = "50em")
public class PersonListView extends StandardListView<Person> {
}