package com.company.onboarding.view.hobby;

import com.company.onboarding.entity.Hobby;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "hobbies/:id", layout = MainView.class)
@ViewController("Hobby.detail")
@ViewDescriptor("hobby-detail-view.xml")
@EditedEntityContainer("hobbyDc")
public class HobbyDetailView extends StandardDetailView<Hobby> {
}