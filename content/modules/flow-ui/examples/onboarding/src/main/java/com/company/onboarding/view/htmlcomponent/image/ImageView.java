package com.company.onboarding.view.htmlcomponent.image;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "ImageView", layout = MainView.class)
@ViewController("ImageView")
@ViewDescriptor("image-view.xml")
public class ImageView extends StandardView {

}