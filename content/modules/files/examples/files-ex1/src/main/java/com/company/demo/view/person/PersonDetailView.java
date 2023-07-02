package com.company.demo.view.person;

import com.company.demo.entity.Person;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "persons/:id", layout = MainView.class)
@ViewController("Person.detail")
@ViewDescriptor("person-detail-view.xml")
@EditedEntityContainer("personDc")
public class PersonDetailView extends StandardDetailView<Person> {

    // tag::file[]
    @ViewComponent
    private InstanceContainer<Person> personDc;

    @Autowired
    private Downloader downloader; // <1>

    @Subscribe("downloadBtn")
    public void onDownloadBtnClick(final ClickEvent<Button> event) {
        byte[] photo = personDc.getItem().getPhoto();
        downloader.download(
                photo,
                personDc.getItem().getName() + "photo", // <2>
                DownloadFormat.JPG // <3>
        );
    }
    // end::file[]
}