package com.company.onboarding.view.component.listbox;

import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "ListBoxView", layout = MainView.class)
@ViewController("ListBoxView")
@ViewDescriptor("list-box-view.xml")
public class ListBoxView extends StandardView {
    // tag::renderer[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;

    // end::renderer[]
    // tag::itemEnabledProvider[]
    @Install(to = "listBox", subject = "itemEnabledProvider")
    private boolean listBoxItemEnabledProvider(final User user) {
        if (user == null) {
            return true;
        }
        return user.getOnboardingStatus() == OnboardingStatus.COMPLETED;

    }
    // end::itemEnabledProvider[]

    // tag::renderer[]
    @Supply(to = "listBox", subject = "renderer")
    private ComponentRenderer<HorizontalLayout, User> listBoxRenderer() {
        return new ComponentRenderer<>(user -> {
            FileRef fileRef = user.getPicture();
            HorizontalLayout row = uiComponents.create(HorizontalLayout.class);
            row.setAlignItems(FlexComponent.Alignment.END);
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                image.setClassName("user-picture");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                row.add(image);
            }
            row.add(new Span(user.getFirstName() + ", " + user.getLastName()));
            return row;
        });
    }
    // end::renderer[]
}