package com.company.onboarding.view.main;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {
    // tag::initialLayout[]
    @ViewComponent
    private JmixImage<Object> urlImage;

    @Subscribe
    public void onInit(final InitEvent event) {
        urlImage.setSrc("https://www.jmix.io/uploads/framework_image_9efadbc372.svg");
        urlImage.setWidth("100%");
        urlImage.setHeight("100%");
    }

    @Subscribe(id = "urlImage", subject = "singleClickListener")
    public void onUrlImageClick(final ClickEvent<JmixImage<?>> event) {
        Notification.show("Clicked!");
    }
    // end::initialLayout[]
}
