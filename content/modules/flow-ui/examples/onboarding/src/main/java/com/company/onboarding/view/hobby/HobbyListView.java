package com.company.onboarding.view.hobby;

import com.company.onboarding.entity.Hobby;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "hobbies", layout = MainView.class)
@ViewController("Hobby.list")
@ViewDescriptor("hobby-list-view.xml")
@LookupComponent("hobbiesDataGrid")
@DialogMode(width = "64em")
public class HobbyListView extends StandardListView<Hobby> {
}